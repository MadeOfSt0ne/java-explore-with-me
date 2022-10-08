package ru.practicum.explore.mappers;

import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.user.User;

import static ru.practicum.explore.utils.DateTimeFormat.FORMATTER;

/**
 * Маппер для комментариев
 */
public class CommentMapper {

    /**
     * Создание дто из комментария
     */
    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getCreatedOn().format(FORMATTER),
                comment.getEditedOn() == null ? null : comment.getEditedOn().format(FORMATTER),
                new CommentDto.UserDto(comment.getAuthor()),
                new CommentDto.EventDto(comment.getEvent())
        );
    }

    /**
     * Создание нового комментария
     */
    public static Comment toComment(String text, User author, Event event) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(author);
        comment.setEvent(event);
        return comment;
    }
}
