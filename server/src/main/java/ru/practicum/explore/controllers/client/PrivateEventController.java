package ru.practicum.explore.controllers.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.models.event.dto.EventShortDto;
import ru.practicum.explore.models.event.dto.NewEventDto;
import ru.practicum.explore.models.event.dto.UpdateEventRequestDto;
import ru.practicum.explore.services.client.PrivateEventService;
import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

/**
 * Публичный контроллер для работы с событиями для авторизованных пользователей
 */
@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService eventService;

    /**
     * Получение списка своих событий
     */
    @GetMapping
    public List<EventShortDto> getOwnEvents(@PathVariable(value = "userId") long userId,
                               @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("PRIVATE: User={} get own events", userId);
        return eventService.getOwnEvents(userId, from, size);
    }

    /**
     * Обновление своего события
     */
    @PatchMapping
    public EventFullDto updateEvent(@PathVariable(value = "userId") long userId,
                                    @RequestBody UpdateEventRequestDto updateEventRequestDto) {
        log.info("PRIVATE: User={} update event={}", userId, updateEventRequestDto);
        return eventService.updateEvent(userId, updateEventRequestDto);
    }

    /**
     * Добавление нового события
     */
    @PostMapping
    public EventFullDto addNewEvent(@PathVariable(value = "userId") long userId,
                                    @RequestBody NewEventDto newEventDto) {
        log.info("PRIVATE: User={} add new event={}", userId, newEventDto);
        return eventService.addNewEvent(userId, newEventDto);
    }

    /**
     * Поиск события по идентификатору
     */
    @GetMapping(path = "/{eventId}")
    public EventFullDto getEvent(@PathVariable(value = "userId") long userId,
                                 @PathVariable(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} get event={}", userId, eventId);
        return eventService.getEvent(userId, eventId);
    }

    /**
     * Отмена своего события
     */
    @PatchMapping(path = "/{eventId}")
    public EventFullDto cancelEvent(@PathVariable(value = "userId") long userId,
                                    @PathVariable(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} cancel event={}", userId, eventId);
        return eventService.cancelEvent(userId, eventId);
    }

    /**
     * Получение списка заявок на участие в своем событии
     */
    @GetMapping(path = "/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable(value = "userId") long userId,
                                                     @PathVariable(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} get requests for event={}", userId, eventId);
        return eventService.getRequests(userId, eventId);
    }

    /**
     * Подтверждение заявки на участие в своем событии
     */
    @PatchMapping(path = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable(value = "userId") long userId,
                                                  @PathVariable(value = "eventId") long eventId,
                                                  @PathVariable(value = "reqId") long requestId) {
        log.info("PRIVATE: User={} confirm request={} for event={}", userId, requestId, eventId);
        return eventService.confirmRequest(userId, eventId, requestId);
    }

    /**
     * Отклонение заявки на участие в своем событии
     */
    @PatchMapping(path = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable(value = "userId") long userId,
                                                  @PathVariable(value = "eventId") long eventId,
                                                  @PathVariable(value = "reqId") long requestId) {
        log.info("PRIVATE: User={} reject request={} for event={}", userId, requestId, eventId);
        return eventService.rejectRequest(userId, eventId, requestId);
    }
}
