package ru.practicum.explore.mappers;

import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.user.User;

import java.time.format.DateTimeFormatter;

/**
 * Маппер для комментариев
 */
public class CommentMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Создание дто из комментария
     */
    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getCreatedOn().format(FORMATTER),
                comment.getEditedOn() == null ? "Not edited." : comment.getEditedOn().format(FORMATTER),
                new CommentDto.UserDto(comment.getAuthor().getId(), comment.getAuthor().getName()),
                new CommentDto.EventDto(comment.getEvent().getId(), comment.getEvent().getTitle())
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
