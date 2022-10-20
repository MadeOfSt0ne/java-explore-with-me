package ru.practicum.explore.models.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто для создания/редактирования комментария
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortCommentDto {
    /**
     * Текст комментария
     */
    private String text;
}
