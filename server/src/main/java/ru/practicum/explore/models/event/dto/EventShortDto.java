package ru.practicum.explore.models.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore.models.comment.Comment;

import java.util.List;

/**
 * Сокращенное дто события
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    /**
     * Краткое описание
     */
    private String annotation;
    /**
     * Дто категории, к которой относится данное событие
     */
    private CategoryDto category;
    /**
     * Количество подтвержденных запросов на участие
     */
    private int confirmedRequests;
    /**
     * Дата и время на которые намечено событие в строковом формате
     */
    private String eventDate;
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Дто инициатора события
     */
    private UserShortDto initiator;
    /**
     * Нужно ли оплачивать участие
     */
    private boolean paid;
    /**
     * Заголовок
     */
    private String title;
    /**
     * Количество просмотров события
     */
    private int views;
    /**
     * Комментарии
     */
    private List<Comment> comments;


    /**
     * Дто категории
     */
    @Data
    @AllArgsConstructor
    public static class CategoryDto {
        /**
         * Идентификатор
         */
        private long id;
        /**
         * Название
         */
        private String name;
    }

    /**
     * Дто инициатора события
     */
    @Data
    @AllArgsConstructor
    public static class UserShortDto {
        /**
         * Идентификатор
         */
        private long id;
        /**
         * Имя
         */
        private String name;
    }
}
