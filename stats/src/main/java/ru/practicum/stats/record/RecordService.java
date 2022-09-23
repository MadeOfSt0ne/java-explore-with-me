package ru.practicum.stats.record;

import ru.practicum.stats.record.dto.EndpointHit;
import ru.practicum.stats.record.dto.ViewStats;

import java.util.List;

public interface RecordService {

    /**
     * Сохранение информации о том, что к эндпоинту был запрос
     */
    void addRecord(EndpointHit endpointHit);

    /**
     * Получение статистики по посещениям
     */
    List<ViewStats> getRecords(String start, String end, String[] uris, boolean unique);
}
