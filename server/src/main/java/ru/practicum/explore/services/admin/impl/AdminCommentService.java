package ru.practicum.explore.services.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;
import ru.practicum.explore.repository.CommentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminCommentService {

    private final CommentRepository commRepo;

    public Comment editComment(long commentId, ShortCommentDto shortCommentDto) {
        Comment comment = commRepo.findById(commentId).orElseThrow();
        comment.setText(shortCommentDto.getText());
        comment.setEditedOn(LocalDateTime.now());
        commRepo.save(comment);
        return comment;
    }

    public void removeComment(long commentId) {
        commRepo.deleteById(commentId);
    }
}
