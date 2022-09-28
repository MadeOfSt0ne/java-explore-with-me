package ru.practicum.explore.utils.RestTemplateClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто статистики
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {
    /**
     * Название приложения сделавшего запрос
     */
    private String app;
    /**
     * Адрес, по которому сделан запрос
     */
    private String uri;
    /**
     * Количество просмотров
     */
    private int hits;
}
