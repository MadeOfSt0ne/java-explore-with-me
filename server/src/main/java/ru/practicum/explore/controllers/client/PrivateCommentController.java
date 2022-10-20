package ru.practicum.explore.controllers.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.mappers.CommentMapper;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;
import ru.practicum.explore.services.client.impl.PrivateCommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Публичный контроллер для работы с комментариями для авторизованных пользователей
 */
@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {

    private final PrivateCommentService commentService;

    /**
     * Добавление нового комментария
     */
    @PostMapping(path = "/new/{eventId}")
    public CommentDto addNewComment(@PathVariable(value = "userId") long userId,
                                    @PathVariable(value = "eventId") long eventId,
                                    @RequestBody ShortCommentDto shortCommentDto) {
        log.info("PRIVATE: User={} add new comment={} to event={}", userId, shortCommentDto, eventId);
        Comment comment = commentService.addComment(userId, eventId, shortCommentDto);
        return CommentMapper.toCommentDto(comment);
    }

    /**
     * Редактирование своего комментария
     */
    @PatchMapping(path = "/{commentId}")
    public CommentDto updateComment(@PathVariable(value = "userId") long userId,
                                    @PathVariable(value = "commentId") long commentId,
                                    @RequestBody ShortCommentDto shortCommentDto) {
        log.info("PRIVATE: User={} edit comment={} with text={}", userId, commentId, shortCommentDto);
        Comment updated = commentService.editComment(userId, commentId, shortCommentDto);
        return CommentMapper.toCommentDto(updated);
    }

    /**
     * Получение списка своих комментариев
     */
    @GetMapping
    public List<CommentDto> getOwnComments(@PathVariable(value = "userId") long userId,
                                           @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("PRIVATE: User={} get own comments", userId);
        List<Comment> comments = commentService.getComments(userId, from, size);
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    /**
     * Удаление своего комментария
     */
    @DeleteMapping(path = "/{commentId}")
    public void deleteOwnComment(@PathVariable(value = "userId") long userId,
                                   @PathVariable(value = "commentId") long commentId) {
        log.info("PRIVATE: User={} deletes comment={}", userId, commentId);
        commentService.removeComment(userId, commentId);
    }
}
