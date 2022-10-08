package ru.practicum.explore.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.mappers.CommentMapper;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;
import ru.practicum.explore.services.admin.impl.AdminCommentService;

/**
 * Приватный api для работы с комментариями
 */
@Slf4j
@RestController
@RequestMapping(path = "/admin/comments/{commentId}")
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService commentService;

    /**
     * Редактирование комментария
     */
    @PatchMapping
    public CommentDto updateComment(@PathVariable(value = "commentId") long commentId,
                                    @RequestBody ShortCommentDto shortCommentDto) {
        log.info("ADMIN: Edit comment={} with text={}", commentId, shortCommentDto);
        Comment comment = commentService.editComment(commentId, shortCommentDto);
        return CommentMapper.toCommentDto(comment);
    }

    /**
     * Удаление комментария
     */
    @DeleteMapping
    public void deleteComment(@PathVariable(value = "commentId") long commentId) {
        log.info("ADMIN: Delete comment={}", commentId);
        commentService.removeComment(commentId);
    }
}
