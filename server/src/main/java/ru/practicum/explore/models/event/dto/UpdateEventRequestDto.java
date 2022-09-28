package ru.practicum.explore.models.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто для изменения события его инициатором
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDto {
    /**
     * Краткое описание
     */
    private String annotation;
    /**
     * Идентификатор категории, к которой относится данное событие
     */
    private Long categoryId;
    /**
     * Полное описание события
     */
    private String description;
    /**
     * Дата и время на которые намечено событие в строковом формате
     */
    private String eventDate;
    /**
     * Идентификатор
     */
    private long eventId;
    /**
     * Нужно ли оплачивать участие
     */
    private Boolean paid;
    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    private Integer participantLimit;
    /**
     * Заголовок
     */
    private String title;
}
