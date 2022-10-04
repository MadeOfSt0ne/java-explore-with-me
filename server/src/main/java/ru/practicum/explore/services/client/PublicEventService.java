package ru.practicum.explore.services.client;

import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.models.event.dto.EventShortDto;
import ru.practicum.explore.models.event.dto.PublicEventsRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Сервис для работы с событиями
 */
public interface PublicEventService {

    /**
     * Получение событий с возможностью фильтрации
     */
    List<EventShortDto> getEventsFiltered(PublicEventsRequest request);

    /**
     * Получение подробной информации о событии по его идентификатору
     */
    EventFullDto getEvent(long eventId);

    /**
     * Отправка статистики
     */
    void makeAndSendEndpointHit(String app, HttpServletRequest request);
}
