package ru.practicum.explore.models.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто для создания нового события
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    /**
     * Краткое описание
     */
    private String annotation;
    /**
     * Идентификатор категории, к которой относится данное событие
     */
    private long categoryId;
    /**
     * Полное описание события
     */
    private String description;
    /**
     * Дата и время на которые намечено событие в строковом формате
     */
    private String eventDate;
    /**
     * Нужно ли оплачивать участие
     */
    private boolean paid;
    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    private int participantLimit;
    /**
     * Нужна ли пре-модерация заявок на участие
     */
    private boolean requestModeration;
    /**
     * Заголовок
     */
    private String title;
    /**
     * Дто локации, в которой происходит событие
     */
    private Location location;

    /**
     * Дто локации
     */
    @Data
    @AllArgsConstructor
    public static class Location {
        /**
         * Долгота
         */
        private float lat;
        /**
         * Широта
         */
        private float lon;
    }
}
