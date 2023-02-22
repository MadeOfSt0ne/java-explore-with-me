package ru.practicum.explore.models.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Дто категории событий
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Название категории
     */
    private String name;
}
