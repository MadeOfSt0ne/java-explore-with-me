package ru.practicum.explore.services.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.exceptions.ValidationException;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repository.CommentRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "comments")
public class PrivateCommentService {

    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final CommentRepository commentRepo;

    /**
     * Добавление нового комментария
     *
     * @param userId id автора
     * @param eventId id события
     * @param shortCommentDto dto комментария
     */
    public Comment addComment(long userId, long eventId, ShortCommentDto shortCommentDto) {
        if (shortCommentDto.getText().isBlank()) {
            throw new ValidationException("Комментарий не может быть пустым.");
        }
        User author = userRepo.findById(userId).orElseThrow();
        if (author.isBanned()) {
            throw new IllegalStateException("Вы не можете оставлять комментарии.");
        }
        Event event = eventRepo.findById(eventId).orElseThrow();
        Comment comment = new Comment();
        comment.setText(shortCommentDto.getText());
        comment.setAuthor(author);
        comment.setEvent(event);
        return commentRepo.save(comment);
    }

    /**
     * Редактирование созданного комментария
     *
     * @param userId id автора
     * @param commentId id комментария
     * @param shortCommentDto dto комментария
     */
    @CachePut(key = "#commentId")
    public Comment editComment(long userId, long commentId, ShortCommentDto shortCommentDto) {
        if (shortCommentDto.getText().isBlank()) {
            throw new ValidationException("Комментарий не может быть пустым");
        }
        Comment comment = commentRepo.findById(commentId).orElseThrow();
        User author = userRepo.findById(userId).orElseThrow();
        if (comment.getAuthor().getId() != userId) {
            throw new IllegalStateException("Нет прав на редактирование комментария.");
        }
        if (author.isBanned()) {
            throw new IllegalStateException("Вы не можете редактировать комментарии.");
        }
        comment.setText(shortCommentDto.getText());
        comment.setEditedOn(LocalDateTime.now());
        commentRepo.save(comment);
        return comment;
    }

    /**
     * Получение списка своих комментариев
     *
     * @param userId id автора
     * @param from офсет
     * @param size размер списка
     */
    public List<Comment> getComments(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        return commentRepo.findCommentsByAuthorId(userId, pageable);
    }

    /**
     * Добавление нового комментария
     *
     * @param userId id автора
     * @param commentId id комментария
     */
    @CacheEvict(key = "#commentId")
    public void removeComment(long userId, long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow();
        User author = userRepo.findById(userId).orElseThrow();
        if (comment.getAuthor().getId() != userId) {
            throw new IllegalStateException("Нет прав на удаление комментария.");
        }
        if (author.isBanned()) {
            throw new IllegalStateException("Вы не можете удалять комментарии.");
        }
        commentRepo.deleteById(commentId);
    }

}
