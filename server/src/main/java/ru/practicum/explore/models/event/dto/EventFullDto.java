package ru.practicum.explore.models.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore.models.comment.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Полное дто события
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto implements Serializable {
    /**
     * Краткое описание
     */
    private String annotation;
    /**
     * Дто категории, к которой относится данное событие
     */
    private CategoryDto category;
    /**
     * Дата и время создания события в строковом формате
     */
    private String createdOn;
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
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    private int participantLimit;
    /**
     * Количество подтвержденных запросов на участие
     */
    private int confirmedRequests;
    /**
     * Дата и время публикации события в строковом формате
     */
    private String publishedOn;
    /**
     * Нужна ли пре-модерация заявок на участие
     */
    private boolean requestModeration;
    /**
     * Состояние жизненного цикла события в строковом формате
     */
    private String state;
    /**
     * Заголовок
     */
    private String title;
    /**
     * Количество просмотров события
     */
    private int views;
    /**
     * Дто локации, в которой происходит событие
     */
    private Location location;
    /**
     * Комментарии
     */
    private List<Comment> comments;

    /**
     * Дто категории
     */
    @Data
    @AllArgsConstructor
    public static class CategoryDto implements Serializable {
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
    public static class UserShortDto implements Serializable {
        /**
         * Идентификатор
         */
        private long id;
        /**
         * Имя
         */
        private String name;
    }

    /**
     * Дто локации
     */
    @Data
    @AllArgsConstructor
    public static class Location implements Serializable {
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
