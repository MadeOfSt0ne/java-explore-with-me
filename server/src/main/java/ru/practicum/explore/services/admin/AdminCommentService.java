package ru.practicum.explore.services.admin;

import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;

/**
 * Сервис для работы с комментариями для администраторов
 */
public interface AdminCommentService {

    /**
     * Редактирование комментария
     */
    CommentDto editComment(long commentId, ShortCommentDto shortCommentDto);

    /**
     * Удаление комментария по идентификатору
     */
    void removeComment(long commentId);
}
