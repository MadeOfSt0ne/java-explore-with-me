package ru.practicum.explore.event.service;

import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;

import java.util.List;

public interface PublicEventService {

    /**
     * Получение событий с возможностью фильтрации
     */
    List<Long> getEventsFiltered(String text, int[] categories, boolean paid, String rangeStart,
                                          String rangeEnd, boolean onlyAvailable, String sort, int from, int size);

    /**
     * Получение подробной информации о событии по его идентификатору
     */
    EventFullDto getEvent(long eventId);
}
