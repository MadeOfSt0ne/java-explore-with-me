package ru.practicum.explore.event.interfaces;

import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;

import java.util.List;

public interface PublicEventService {

    /**
     * Получение событий с возможностью фильтрации
     */
    List<EventShortDto> getEventsFiltered();

    /**
     * Получение подробной информации о событии по его идентификатору
     */
    EventFullDto getEvent(long eventId);
}
