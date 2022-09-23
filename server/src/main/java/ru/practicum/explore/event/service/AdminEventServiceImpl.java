package ru.practicum.explore.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.category.CategoryRepository;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.EventState;
import ru.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final CategoryRepository catRepository;

    /**
     * Метод возвращает полную информацию обо всех событиях подходящих под переданные условия
     */
    @Override
    public List<EventFullDto> getAllEvents(Long[] users, String[] states, Integer[] categories, String rangeStart,
                                           String rangeEnd, int from, int size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);
        return null;
    }

    /**
     * Редактирование данных любого события администратором
     *
     * @param eventId id события
     * @param eventRequest dto события
     */
    @Override
    public EventFullDto editEvent(long eventId, AdminUpdateEventRequest eventRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        Category category = catRepository.findById(eventRequest.getCategoryId()).orElseThrow();
        Event updated = eventRepository.save(EventMapper.toUpdatedEvent(eventRequest, category, event));
        return EventMapper.toEventFullDto(updated);
    }

    /**
     * Публикация события
     *
     * @param eventId id события
     */
    @Override
    public EventFullDto publishEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setPublishedOn(LocalDateTime.now().withNano(0));;
        event.setEventState(EventState.PUBLISHED);
        Event published = eventRepository.save(event);
        return EventMapper.toEventFullDto(published);
    }

    /**
     * Отклонение события
     *
     * @param eventId id события
     */
    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setEventState(EventState.CANCELLED);
        Event rejected = eventRepository.save(event);
        return EventMapper.toEventFullDto(rejected);
    }
}
