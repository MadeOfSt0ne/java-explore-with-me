package ru.practicum.explore.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.service.PublicEventService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final PublicEventService eventService;

    @GetMapping
    public List<Long> getEventsFiltered(
            @RequestParam(value = "text", required = false, defaultValue = "") String text,
            @RequestParam(value = "categories", required = false, defaultValue = "new int[0]") int[] categories,
            @RequestParam(value = "paid", required = false, defaultValue = "false") boolean paid,
            @RequestParam(value = "rangeStart", required = false, defaultValue = "NOW") String rangeStart,
            @RequestParam(value = "rangeEnd", required = false, defaultValue = "FUTURE") String rangeEnd,
            @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") boolean onlyAvailable,
            @RequestParam(value = "sort", required = false, defaultValue = "EVENT_DATE") String sort,
            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("PUBLIC: Get events with filters: text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}," +
                "onlyAvailable={}, sort={}, from={}, size={}", text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        return eventService.getEventsFiltered(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping(path = "/{id}")
    public EventFullDto getEvent(@PathVariable long id) {
        log.info("PUBLIC: Get event id={}", id);
        return eventService.getEvent(id);
    }
}
