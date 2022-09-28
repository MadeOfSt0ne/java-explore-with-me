package ru.practicum.explore.models.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто категории событий
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Название категории
     */
    private String name;
}
