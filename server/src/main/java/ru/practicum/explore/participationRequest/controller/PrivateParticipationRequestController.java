package ru.practicum.explore.participationRequest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.participationRequest.service.ParticipationRequestService;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateParticipationRequestController {

    private final ParticipationRequestService service;

    @GetMapping
    public List<ParticipationRequestDto> getOwnRequests(@PathVariable(value = "userId") long userId) {
        log.info("PRIVATE: User={} get own requests", userId);
        return service.getOwnRequests(userId);
    }

    @PostMapping
    public ParticipationRequestDto addNewRequest(@PathVariable(value = "userId") long userId,
                                                 @RequestParam(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} add request for event={}", userId, eventId);
        return service.addNewRequest(userId, eventId);
    }

    @PatchMapping(path = "/{requestId}/cancel")
    public ParticipationRequestDto cancelOwnRequest(@PathVariable(value = "userId") long userId,
                                                    @PathVariable(value = "requestId") long requestId) {
        log.info("PRIVATE: User={} cancel own request={}", userId, requestId);
        return service.cancelOwnRequest(userId, requestId);
    }
}
