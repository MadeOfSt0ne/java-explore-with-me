package ru.practicum.explore.services.admin.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.EventMapper;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.event.QEvent;
import ru.practicum.explore.models.event.dto.AdminUpdateEventRequestDto;
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.repository.CategoryRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.services.admin.AdminEventService;
import ru.practicum.explore.utils.RestTemplateClient.ViewsProcessor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explore.utils.DateTimeFormat.DateTimeFormat.FORMATTER;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final CategoryRepository catRepository;
    private final ViewsProcessor viewsProcessor;

    /**
     * Метод возвращает полную информацию обо всех событиях подходящих под переданные условия
     */
    @Override
    public List<EventFullDto> getAllEvents(Long[] users, String[] states, Integer[] categories, String rangeStart,
                                           String rangeEnd, int from, int size) {
        // Дефолтных значений нет в спецификации, так что придумал сам
        LocalDateTime start = (rangeStart == null ? LocalDateTime.now().minusMonths(1) : LocalDateTime.parse(rangeStart, FORMATTER));
        LocalDateTime end = (rangeEnd == null ? LocalDateTime.now().plusMonths(1) : LocalDateTime.parse(rangeEnd, FORMATTER));
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(event.eventDate.between(start, end));
        // Если получен список id инициаторов, то включаем их в параметры поиска
        if (users != null) {
            for (Long id : users) {
                conditions.add(event.initiator.id.eq(id));
            }
        }
        // Если получен список состояний, то включаем их в поиск
        if (states != null) {
            for (String state : states) {
                EventState eventState = getState(state.toLowerCase());
                conditions.add(event.eventState.eq(eventState));
            }
        }
        // Если получен список id категорий, то включаем их в поиск
        if (categories != null) {
            for (Integer catId : categories) {
                conditions.add(event.category.id.eq(Long.valueOf(catId)));
            }
        }
        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();
        Pageable pageable = PageRequest.of(from, size);
        Page<Event> events = eventRepository.findAll(finalCondition, pageable);
        events.forEach(e -> e.setViews(viewsProcessor.getViews(e.getId())));

        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод принимает строку с названием статуса и возвращает соответствующий статус
     *
     * @throws IllegalArgumentException если статус не найден
     */
    private EventState getState(String state) {
        switch (state) {
            case ("pending") : return EventState.PENDING;
            case ("published") : return EventState.PUBLISHED;
            case ("cancelled") : return EventState.CANCELLED;
            default: throw new IllegalArgumentException("Unknown state: " + state + ".");
        }
    }

    /**
     * Редактирование данных любого события администратором
     *
     * @param eventId id события
     * @param eventRequest dto события
     */
    @Override
    public EventFullDto editEvent(long eventId, AdminUpdateEventRequestDto eventRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setViews(viewsProcessor.getViews(eventId));
        Category category = catRepository.findById(eventRequest.getCategoryId()).orElseThrow();
        Event updated = eventRepository.save(EventMapper.toUpdatedEvent(eventRequest, category, event));
        return EventMapper.toEventFullDto(updated);
    }

    /**
     * Публикация события
     *
     * @param eventId id события
     * @throws IllegalStateException если событие не находится в состоянии ожидания публикации или время начала раньше,
     * чем через час от времени публикации
     */
    @Override
    public EventFullDto publishEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!event.getEventState().equals(EventState.PENDING)
                || event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new IllegalStateException();
        }
        event.setViews(viewsProcessor.getViews(eventId));
        event.setPublishedOn(LocalDateTime.now().withNano(0));
        event.setEventState(EventState.PUBLISHED);
        Event published = eventRepository.save(event);
        return EventMapper.toEventFullDto(published);
    }

    /**
     * Отклонение события
     *
     * @param eventId id события
     * @throws IllegalStateException если событие не находится в состоянии ожидания публикации
     */
    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!event.getEventState().equals(EventState.PENDING)) {
            throw new IllegalStateException();
        }
        event.setViews(viewsProcessor.getViews(eventId));
        event.setEventState(EventState.CANCELLED);
        Event rejected = eventRepository.save(event);
        return EventMapper.toEventFullDto(rejected);
    }
}
