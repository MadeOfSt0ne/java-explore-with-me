package ru.practicum.stats.record.dto;

import lombok.NoArgsConstructor;
import ru.practicum.stats.record.Record;

@NoArgsConstructor
public class RecordMapper {

    public static Record toRecord(EndpointHit hit) {
        Record record = new Record();
        record.setApp(hit.getApp());
        record.setIp(hit.getIp());
        record.setUri(hit.getUri());
        return record;
    }
}
