package ru.practicum.explore.controllers.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.services.client.PrivateCommentService;

import java.util.List;

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
    @PostMapping(path = "/{eventId}")
    public CommentDto addNewComment(@PathVariable(value = "userId") long userId,
                                    @PathVariable(value = "eventId") long eventId,
                                    @RequestParam(value = "text") String text) {
        log.info("PRIVATE: User={} add new comment={} to event={}", userId, text, eventId);
        return null;
    }

    /**
     * Редактирование своего комментария
     */
    @PatchMapping(path = "/{commentId}")
    public CommentDto updateComment(@PathVariable(value = "userId") long userId,
                                    @PathVariable(value = "commentId") long commentId,
                                    @RequestParam(value = "text") String text) {
        log.info("PRIVATE: User={} edit comment={} with text={}", userId, commentId, text);
        return null;
    }

    /**
     * Получение списка своих комментариев
     */
    @GetMapping(path = "/own")
    public List<CommentDto> getOwnComments(@PathVariable(value = "userId") long userId) {
        log.info("PRIVATE: User={} get own comments", userId);
        return null;
    }

    /**
     * Удаление своего комментария
     */
    @DeleteMapping(path = "/{commentId}")
    public String deleteOwnComment(@PathVariable(value = "userId") long userId,
                                   @PathVariable(value = "commentId") long commentId) {
        log.info("PRIVATE: User={} deletes comment={}", userId, commentId);
        return null;
    }
}
