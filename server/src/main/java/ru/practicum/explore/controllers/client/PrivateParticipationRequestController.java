package ru.practicum.explore.controllers.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.services.client.ParticipationRequestService;

import java.util.List;

/**
 * Публичный контроллер для работы с заявками на участие в событии для авторизованных пользователей
 */
@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateParticipationRequestController {

    private final ParticipationRequestService service;

    /**
     * Получение списка своих заявок на участие в событиях
     */
    @GetMapping
    public List<ParticipationRequestDto> getOwnRequests(@PathVariable(value = "userId") long userId) {
        log.info("PRIVATE: User={} get own requests", userId);
        return service.getOwnRequests(userId);
    }

    /**
     * Добавление новой заявки на участие в событии
     */
    @PostMapping
    public ParticipationRequestDto addNewRequest(@PathVariable(value = "userId") long userId,
                                                 @RequestParam(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} add request for event={}", userId, eventId);
        return service.addNewRequest(userId, eventId);
    }

    /**
     * Отмена своей заявки на участие в событии
     */
    @PatchMapping(path = "/{requestId}/cancel")
    public ParticipationRequestDto cancelOwnRequest(@PathVariable(value = "userId") long userId,
                                                    @PathVariable(value = "requestId") long requestId) {
        log.info("PRIVATE: User={} cancel own request={}", userId, requestId);
        return service.cancelOwnRequest(userId, requestId);
    }
}
