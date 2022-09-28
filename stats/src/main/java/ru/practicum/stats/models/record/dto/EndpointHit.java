package ru.practicum.stats.models.record.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто записи статистики
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    /**
     * Название приложения
     */
    private String app;
    /**
     * Адрес, по которому сделан запрос
     */
    private String uri;
    /**
     * Ip адрес с которого сделан запрос
     */
    private String ip;
}
