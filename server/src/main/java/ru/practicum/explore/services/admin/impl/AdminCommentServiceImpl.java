package ru.practicum.explore.services.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.CommentMapper;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.repository.CommentRepository;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.services.admin.AdminCommentService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commRepo;

    @Override
    public CommentDto editComment(long commentId, String text) {
        Comment comment = commRepo.findById(commentId).orElseThrow();
        comment.setText(text);
        comment.setEditedOn(LocalDateTime.now());
        commRepo.save(comment);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public void removeComment(long commentId) {
        commRepo.deleteById(commentId);
    }
}
