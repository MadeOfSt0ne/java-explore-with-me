package ru.practicum.explore.models.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.user.User;

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
    public static class UserDto {
        /**
         * Идентификатор
         */
        private long id;
        /**
         * Имя
         */
        private String name;

        public UserDto(User author) {
            this.id = author.getId();
            this.name = author.getName();
        }
    }

    /**
     * Дто события
     */
    @Data
    public static class EventDto {
        /**
         * Идентификатор
         */
        private long id;
        /**
         * Заголовок
         */
        private String title;

        public EventDto(Event event) {
            this.id = event.getId();
            this.title = event.getTitle();
        }
    }
}
