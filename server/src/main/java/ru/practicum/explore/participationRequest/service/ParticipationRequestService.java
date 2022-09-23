package ru.practicum.explore.participationRequest.service;

import ru.practicum.explore.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

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
