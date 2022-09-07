package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventShortDto;
import ru.practicum.explore.event.dto.NewEventDto;
import ru.practicum.explore.event.dto.UpdateEventRequest;
import ru.practicum.explore.event.interfaces.PrivateEventService;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService eventService;

    @GetMapping
    public List<EventShortDto> getOwnEvents(@PathVariable(value = "userId") long userId,
                               @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("PRIVATE: User={} get own events", userId);
        return null;
    }

    @PatchMapping
    public EventFullDto updateEvent(@PathVariable(value = "userId") long userId,
                                    @RequestBody UpdateEventRequest updateEventRequest) {
        log.info("PRIVATE: User={} update event={}", userId, updateEventRequest);
        return null;
    }

    @PostMapping
    public EventFullDto addNewEvent(@PathVariable(value = "userId") long userId,
                                    @RequestBody NewEventDto newEventDto) {
        log.info("PRIVATE: User={} add new event={}", userId, newEventDto);
        return null;
    }

    @GetMapping(path = "/{eventId}")
    public EventFullDto getEvent(@PathVariable(value = "userId") long userId,
                                 @PathVariable(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} get event={}", userId, eventId);
        return null;
    }

    @PatchMapping(path = "/{eventId}")
    public EventFullDto cancelEvent(@PathVariable(value = "userId") long userId,
                                    @PathVariable(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} cancel event={}", userId, eventId);
        return null;
    }

    @GetMapping(path = "/{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable(value = "userId") long userId,
                                                     @PathVariable(value = "eventId") long eventId) {
        log.info("PRIVATE: User={} get requests for event={}", userId, eventId);
        return null;
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable(value = "userId") long userId,
                                                  @PathVariable(value = "eventId") long eventId,
                                                  @PathVariable(value = "reqId") long requestId) {
        log.info("PRIVATE: User={} confirm request={} for event={}", userId, requestId, eventId);
        return null;
    }

    @PatchMapping(path = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable(value = "userId") long userId,
                                                  @PathVariable(value = "eventId") long eventId,
                                                  @PathVariable(value = "reqId") long requestId) {
        log.info("PRIVATE: User={} reject request={} for event={}", userId, requestId, eventId);
        return null;
    }
}
