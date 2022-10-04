package ru.practicum.explore.services.client;

import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Сервис для работы с запросами на участие в событии
 */
public interface ParticipationRequestService {

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    List<ParticipationRequestDto> getOwnRequests(long userId);

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     */
    ParticipationRequestDto addNewRequest(long userId, long eventId);

    /**
     * Отмена своего запроса на участие в событии
     */
    ParticipationRequestDto cancelOwnRequest(long userId, long requestId);
}
