package ru.practicum.explore.event.service;

import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.dto.PublicEventsRequest;

import java.util.List;

public interface PublicEventService {

    /**
     * Получение событий с возможностью фильтрации
     */
    List<EventShortDto> getEventsFiltered(PublicEventsRequest request);

    /**
     * Получение подробной информации о событии по его идентификатору
     */
    EventFullDto getEvent(long eventId);
}
