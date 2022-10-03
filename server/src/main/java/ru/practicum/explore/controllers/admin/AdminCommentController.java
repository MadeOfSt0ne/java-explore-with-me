package ru.practicum.explore.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.services.admin.AdminCommentService;

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
                                    @RequestParam(value = "text") String text) {
        log.info("ADMIN: Edit comment={} with text={}", commentId, text);
        return commentService.editComment(commentId, text);
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
