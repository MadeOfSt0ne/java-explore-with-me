package ru.practicum.explore.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.QEvent;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.dto.PublicEventsRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;

    /**
     * Получение событий с возможностью фильтрации
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsFiltered(PublicEventsRequest request) {
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        // Если текст не пустой, то добавляем в условия
        if (!request.getText().isBlank()) {
            conditions.add(event.annotation.likeIgnoreCase(request.getText())
                    .or(event.description.likeIgnoreCase(request.getText())));
        }
        // Если список идентификаторов категорий не пустой, то добавляем в условия
        if (request.getCategories().size() != 0) {
            for (Integer catId : request.getCategories()) {
                conditions.add(event.category.id.eq(catId.longValue()));
            }
        }
        // Если участие в событии платное, то ставим флаг true
        if (request.isPaid()) {
            conditions.add(event.paid.isTrue());
        } else {
            conditions.add(event.paid.isFalse());
        }
        // Добавляем условие что начало события должно быть в заданном промежутке времени
        conditions.add(event.eventDate.between(request.getRangeStart(), request.getRangeEnd()));
        // Собираем все условия в одно
        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();
        Pageable pageable = PageRequest.of(request.getFrom(), request.getSize());
        Page<Event> events = eventRepository.findAll(finalCondition, pageable);


        return events.stream()
                .sorted(getComparator(request))
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    private Comparator<Event> getComparator(PublicEventsRequest request) {
        if (request.getSort().equals(PublicEventsRequest.Sort.VIEWS)) {
            return Comparator.comparing(Event::getEventDate);
        } else {
            return Comparator.comparing(Event::getEventDate);
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
        return EventMapper.toEventFullDto(event);
    }
}
