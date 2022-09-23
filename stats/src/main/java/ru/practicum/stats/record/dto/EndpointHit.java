package ru.practicum.stats.record.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}