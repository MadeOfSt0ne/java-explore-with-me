package ru.practicum.explore.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.services.admin.UserService;
import ru.practicum.explore.models.user.dto.UserDto;

import java.util.List;

/**
 * Приватный api для работы с пользователями
 */
@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    /**
     * Добавление нового пользователя
     */
    @PostMapping
    public UserDto addNewUser(@RequestBody UserDto userDto) {
        log.info("ADMIN: Add new user {}", userDto);
        return userService.addNewUser(userDto);
    }

    /**
     * Получение списка всех или определенных пользователей
     */
    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(value = "ids", required = false) Long[] ids,
                                     @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("ADMIN: Get users");
        return userService.getUsers(ids, from, size);
    }

    /**
     * Удаление пользователя
     */
    @DeleteMapping(path = "/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("ADMIN: Delete user id={}", userId);
        userService.deleteUser(userId);
    }

    /**
     * Запретить пользователю оставлять комментарии
     */
    @PatchMapping(path = "/{userId}/ban")
    public void banUser(@PathVariable(value = "userId") long userId) {
        log.info("ADMIN: Ban user={}", userId);
        userService.ban(userId);
    }

    /**
     * Разрешить пользователю оставлять комментарии
     */
    @PatchMapping(path = "/{userId}/unban")
    public void unbanUser(@PathVariable(value = "userId") long userId) {
        log.info("ADMIN: Unban user={}", userId);
        userService.unban(userId);
    }
}
