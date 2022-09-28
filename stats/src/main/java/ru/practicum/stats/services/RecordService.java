package ru.practicum.stats.services;

import ru.practicum.stats.models.record.dto.EndpointHit;
import ru.practicum.stats.models.record.dto.ViewStats;

import java.util.List;

/**
 * Сервис для работы со статистикой
 */
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
