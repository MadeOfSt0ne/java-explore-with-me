package ru.practicum.explore.models.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Дто для создания новой подборки
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    /**
     * Закреплена ли категория на главной странице
     */
    private boolean pinned;
    /**
     * Название категории
     */
    private String title;
    /**
     * Список событий, относящихся к данной категории
     */
    private List<Long> events;
}
