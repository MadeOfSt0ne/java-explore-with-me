package ru.practicum.explore.event.interfaces;

import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.dto.NewEventDto;
import ru.practicum.explore.event.dto.UpdateEventRequest;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {

    /**
     * Получение событий, добавленных текущим пользователем
     */
    List<EventShortDto> getOwnEvents(long userId, int from, int size);

    /**
     * Изменение события добавленного текущим пользователем
     */
    EventFullDto updateEvent(long userId, UpdateEventRequest updateEventRequest);

    /**
     * Добавление нового события
     */
    EventFullDto addNewEvent(long userId, NewEventDto newEventDto);

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     */
    EventFullDto getEvent(long userId, long eventId);

    /**
     * Отмена события добавленного пользователем
     */
    EventFullDto cancelEvent(long userId, long eventId);

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     */
    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    /**
     * Подтверждение чужой заявки на участие в событии текущего пользователя
     */
    ParticipationRequestDto confirmRequest(long userId, long eventId, long requestId);

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя
     */
    ParticipationRequestDto rejectRequest(long userId, long eventId, long requestId);

}
