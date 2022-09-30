package ru.practicum.explore.services.client;

import ru.practicum.explore.models.comment.dto.CommentDto;

import java.util.List;

/**
 * Сервис для работы с комментариями для зарегистрированных пользователей
 */
public interface PrivateCommentService {

    /**
     * Добавление нового комментария
     */
    CommentDto addComment(long userId, long eventId, String text);

    /**
     * Редактирование своего комментария
     */
    CommentDto editComment(long userId, long commentId, String text);

    /**
     * Получение списка своих комментариев
     */
    List<CommentDto> getComments(long userId);

    /**
     * Удаление своего комментария
     */
    void removeComment(long userId, long commentId);
}
