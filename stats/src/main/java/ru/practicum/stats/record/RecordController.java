package ru.practicum.stats.record;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.record.dto.EndpointHit;
import ru.practicum.stats.record.dto.ViewStats;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping(path = "/hit")
    public void addRecord(EndpointHit endpointHit) {
        log.info("STATS: Add record={}", endpointHit);
        recordService.addRecord(endpointHit);
    }

    @GetMapping(path = "/stats")
    public List<ViewStats> getRecords(@RequestParam(value = "start") String start,
                                      @RequestParam(value = "end") String end,
                                      @RequestParam(value = "uris") String[] uris,
                                      @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        log.info("STATS: Get records from {} to {} at uris {} with unique={}", start, end, uris, unique);
        return recordService.getRecords(start, end, uris, unique);
    }
}
