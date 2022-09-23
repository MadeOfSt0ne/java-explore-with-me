package ru.practicum.explore.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.service.AdminEventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final AdminEventService adminEventService;

    @GetMapping
    public List<EventFullDto> getAllEvents(
            @RequestParam(value = "users") Long[] users,
            @RequestParam(value = "states") String[] states,
            @RequestParam(value = "categories") Integer[] categories,
            @RequestParam(value = "rangeStart") String rangeStart,
            @RequestParam(value = "rangeEnd") String rangeEnd,
            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("ADMIN: Get events with filters: users={}, states={}, categories={}, rangeStart={}, rangeEnd={}," +
                "from={}, size={}", users, states, categories, rangeStart, rangeEnd, from, size);
        return adminEventService.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping(path = "/{eventId}")
    public EventFullDto editEvent(@PathVariable(value = "eventId") long eventId,
                                  @RequestBody AdminUpdateEventRequest eventRequest) {
        log.info("ADMIN: Edit event={} with {}", eventId, eventRequest);
        return adminEventService.editEvent(eventId, eventRequest);
    }

    @PatchMapping(path = "/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable(value = "eventId") long eventId) {
        log.info("ADMIN: Publish event={}", eventId);
        return adminEventService.publishEvent(eventId);
    }

    @PatchMapping(path = "/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable(value = "eventId") long eventId) {
        log.info("ADMIN: Reject event={}", eventId);
        return adminEventService.rejectEvent(eventId);
    }
}
