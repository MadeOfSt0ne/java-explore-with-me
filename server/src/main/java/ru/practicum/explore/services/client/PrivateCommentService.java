package ru.practicum.explore.services.client;

import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;

import java.util.List;

/**
 * Сервис для работы с комментариями для зарегистрированных пользователей
 */
public interface PrivateCommentService {

    /**
     * Добавление нового комментария
     */
    CommentDto addComment(long userId, long eventId, ShortCommentDto shortCommentDto);

    /**
     * Редактирование своего комментария
     */
    CommentDto editComment(long userId, long commentId, ShortCommentDto shortCommentDto);

    /**
     * Получение списка своих комментариев
     */
    List<CommentDto> getComments(long userId, int from, int size);

    /**
     * Удаление своего комментария
     */
    void removeComment(long userId, long commentId);
}
