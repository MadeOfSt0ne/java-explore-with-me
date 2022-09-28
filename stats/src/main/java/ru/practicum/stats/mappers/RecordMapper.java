package ru.practicum.stats.mappers;

import lombok.NoArgsConstructor;
import ru.practicum.stats.models.record.Record;
import ru.practicum.stats.models.record.dto.EndpointHit;

/**
 * Маппер для записей статистики
 */
@NoArgsConstructor
public class RecordMapper {

    /**
     * Создание записи из дто
     */
    public static Record toRecord(EndpointHit hit) {
        Record record = new Record();
        record.setApp(hit.getApp());
        record.setIp(hit.getIp());
        record.setUri(hit.getUri());
        return record;
    }
}
