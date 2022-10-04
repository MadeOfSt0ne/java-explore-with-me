package ru.practicum.stats.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.services.RecordService;
import ru.practicum.stats.models.record.dto.EndpointHit;
import ru.practicum.stats.models.record.dto.ViewStats;

import java.util.List;

/**
 * Api для работы со статистикой просмотров событий
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    /**
     * Добавление записи о просмотре
     */
    @PostMapping(path = "/hit")
    public void addRecord(EndpointHit endpointHit) {
        log.info("STATS: Add record={}", endpointHit);
        recordService.addRecord(endpointHit);
    }

    /**
     * Получение списка записей подходящих под переданные параметры
     */
    @GetMapping(path = "/stats")
    public List<ViewStats> getRecords(@RequestParam(value = "start") String start,
                                      @RequestParam(value = "end") String end,
                                      @RequestParam(value = "uris") String[] uris,
                                      @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        log.info("STATS: Get records from {} to {} at uris {} with unique={}", start, end, uris, unique);
        return recordService.getRecords(start, end, uris, unique);
    }
}
