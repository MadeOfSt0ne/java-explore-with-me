package ru.practicum.explore.mappers;

import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.CommentDto;

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

}
