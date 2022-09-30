package ru.practicum.explore.models.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто комментария к событию
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Текст комментария
     */
    private String text;
    /**
     * Дата и время создания комментария в строковом формате
     */
    private String createdOn;
    /**
     * Дата и время редактирования комментария в строковом формате
     */
    private String editedOn;
    /**
     * Дто автора комментария
     */
    private UserDto user;
    /**
     * Дто события
     */
    private EventDto event;

    /**
     * Дто автора комментария
     */
    @Data
    @AllArgsConstructor
    public static class UserDto {
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
     * Дто события
     */
    @Data
    @AllArgsConstructor
    public static class EventDto {
        /**
         * Идентификатор
         */
        private long id;
        /**
         * Заголовок
         */
        private String title;
    }
}
