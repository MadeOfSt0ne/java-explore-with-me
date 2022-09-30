package ru.practicum.explore.utils.CommentProcessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.repositroy.CommentRepository;

import java.util.List;

/**
 * Класс собирает комментарии к событию
 */
@Service
@RequiredArgsConstructor
public class CommentProcessor {

    private final CommentRepository commentRepo;

    /**
     * Получение списка комментариев по идентификатору события
     */
    public List<Comment> getCommentsForEvent(long eventId) {
        return commentRepo.findCommentsByEventId(eventId);
    }
}
