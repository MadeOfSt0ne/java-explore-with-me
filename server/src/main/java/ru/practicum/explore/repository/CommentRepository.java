package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.models.comment.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Получение списка комментариев по идентификатору автора
     */
    List<Comment> findCommentsByAuthorId(long authorId);

    /**
     *  Получение списка комментариев по идентификатору события
     */
    List<Comment> findCommentsByEventId(long eventId);
}
