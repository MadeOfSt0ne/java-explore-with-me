package ru.practicum.explore.services.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.exceptions.ValidationException;
import ru.practicum.explore.mappers.CommentMapper;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.repository.CommentRepository;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repository.UserRepository;
import ru.practicum.explore.services.client.PrivateCommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final CommentRepository commentRepo;

    @Override
    public CommentDto addComment(long userId, long eventId, String text) {
        if (text.isBlank()) {
            throw new ValidationException("Комментарий не может быть пустым.");
        }
        User author = userRepo.findById(userId).orElseThrow();
        if (author.isBanned()) {
            throw new IllegalStateException("Вы не можете оставлять комментарии.");
        }
        Event event = eventRepo.findById(eventId).orElseThrow();
        Comment comment = commentRepo.save(CommentMapper.toComment(text, author, event));
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto editComment(long userId, long commentId, String text) {
        if (text.isBlank()) {
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
        comment.setText(text);
        comment.setEditedOn(LocalDateTime.now());
        commentRepo.save(comment);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public List<CommentDto> getComments(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Comment> comments = commentRepo.findCommentsByAuthorId(userId, pageable);
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
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
