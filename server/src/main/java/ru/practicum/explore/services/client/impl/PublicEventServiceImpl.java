package ru.practicum.explore.services.client.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.exceptions.ValidationException;
import ru.practicum.explore.mappers.EventMapper;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.repositroy.EventRepository;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.event.QEvent;
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.models.event.dto.EventShortDto;
import ru.practicum.explore.models.event.dto.PublicEventsRequest;
import ru.practicum.explore.services.client.PublicEventService;
import ru.practicum.explore.utils.CommentProcessor.CommentProcessor;
import ru.practicum.explore.utils.RestTemplateClient.EndpointHit;
import ru.practicum.explore.utils.RestTemplateClient.EventClient;
import ru.practicum.explore.utils.RestTemplateClient.ViewsProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;
    private final ViewsProcessor viewsProcessor;
    private final EventClient eventClient;
    private final CommentProcessor commentProcessor;

    /**
     * Получение событий с возможностью фильтрации
     *
     * @param request объект, содержащий параметры запроса
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsFiltered(PublicEventsRequest request) {
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        // Событие должно быть опубликованным
        conditions.add(event.eventState.eq(EventState.PUBLISHED));
        // Если текст не пустой, то добавляем в условия
        if (!request.getText().isBlank()) {
            conditions.add(event.annotation.likeIgnoreCase(request.getText())
                    .or(event.description.likeIgnoreCase(request.getText())));
        }
        // Если список идентификаторов категорий не пустой, то добавляем в условия
        if (request.getCategories().size() != 0) {
            for (Integer catId : request.getCategories()) {
                conditions.add(event.category.id.eq(Long.valueOf(catId)));
            }
        }
        // Если участие в событии платное, то ставим флаг true
        if (request.isPaid()) {
            conditions.add(event.paid.isTrue());
        } else {
            conditions.add(event.paid.isFalse());
        }
        // Добавляем условие, что начало события должно быть в заданном промежутке времени
        conditions.add(event.eventDate.between(request.getRangeStart(), request.getRangeEnd()));
        // Собираем все условия в одно
        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();
        Pageable pageable = PageRequest.of(request.getFrom(), request.getSize());
        // Делаем запрос в базу
        Page<Event> events = eventRepository.findAll(finalCondition, pageable);
        // Проставляем всем событиям просмотры
        events.forEach(e -> e.setViews(viewsProcessor.getViews(e.getId())));
        // Если нужны только имеющие свободные места, то добавляем фильтр на свободные места
        if (request.isOnlyAvailable()) {
            return events.stream()
                    .filter(e -> e.getParticipantLimit() > e.getConfirmedRequests())
                    .sorted(getComparator(request.getSort().toLowerCase()))
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toList());
        }
        return events.stream()
                    .sorted(getComparator(request.getSort().toLowerCase()))
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toList());
    }

    /**
     * Метод возвращает компаратор в зависимости от выбранного типа сортировки
     *
     * @param sort способ сортировки
     * @throws IllegalArgumentException если тип сортировки не найден
     */
    private Comparator<Event> getComparator(String sort) {
        switch (sort) {
            case ("views") : return Comparator.comparing(Event::getViews);
            case ("event_date") : return Comparator.comparing(Event::getEventDate);
            default: throw new ValidationException("Unknown sort: " + sort + ".");
        }
    }

    /**
     * Получение подробной информации о событии по его идентификатору
     *
     * @param eventId id события
     */
    @Override
    public EventFullDto getEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!event.getEventState().equals(EventState.PUBLISHED)) {
            throw new NoSuchElementException("Event is not published");
        }
        event.setViews(viewsProcessor.getViews(eventId));
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Отправка статистики
     */
    @Override
    public void makeAndSendEndpointHit(String app, HttpServletRequest request) {
        eventClient.sendHit(new EndpointHit(app, request.getRequestURI(), request.getRemoteAddr()));
    }
}
