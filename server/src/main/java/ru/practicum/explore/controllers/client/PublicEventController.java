package ru.practicum.explore.controllers.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.models.event.dto.EventShortDto;
import ru.practicum.explore.models.event.dto.PublicEventsRequest;
import ru.practicum.explore.services.client.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Публичный контроллер для запроса событий для всех пользователей, в т.ч. неавторизованных
 */
@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final PublicEventService eventService;

    /**
     * Получение списка всех событий с выборкой по параметрам
     */
    @GetMapping
    public List<EventShortDto> getEventsFiltered(
            @RequestParam(value = "text", required = false, defaultValue = "") String text,
            @RequestParam(value = "categories", required = false, defaultValue = "new int[0]") Integer[] categories,
            @RequestParam(value = "paid", required = false, defaultValue = "false") boolean paid,
            @RequestParam(value = "rangeStart", required = false, defaultValue = "NULL") String rangeStart,
            @RequestParam(value = "rangeEnd", required = false, defaultValue = "NULL") String rangeEnd,
            @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") boolean onlyAvailable,
            @RequestParam(value = "sort", required = false, defaultValue = "EVENT_DATE") String sort,
            @RequestParam(value = "from", required = false, defaultValue = "0") int from,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            HttpServletRequest request) {
        log.info("PUBLIC: Get events with filters: text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}," +
                "onlyAvailable={}, sort={}, from={}, size={}", text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        log.info("Client ip={}, endpoint path={}", request.getRemoteAddr(), request.getRequestURI());
        // Отправка статистики
        eventService.makeAndSendEndpointHit("GetEventsFiltered", request);
        return eventService.getEventsFiltered(PublicEventsRequest.of(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size));
    }

    /**
     * Поиск события по идентификатору
     */
    @GetMapping(path = "/{id}")
    public EventFullDto getEvent(@PathVariable long id, HttpServletRequest request) {
        log.info("PUBLIC: Get event id={}", id);
        log.info("Client ip={}, endpoint path={}", request.getRemoteAddr(), request.getRequestURI());
        // Отправка статистики
        eventService.makeAndSendEndpointHit("GetEventById", request);
        return eventService.getEvent(id);
    }
}
