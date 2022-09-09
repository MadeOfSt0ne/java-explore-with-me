package ru.practicum.explore.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.event.dto.EventShortDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private final EventRepository eventRepository;

    /**
     * Получение событий с возможностью фильтрации
     */
    @Override
    public List<Long> getEventsFiltered(String text, int[] categories, boolean paid, String rangeStart,
                                             String rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        return null;
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
