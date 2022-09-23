package ru.practicum.explore.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.user.service.UserService;
import ru.practicum.explore.user.dto.UserDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PostMapping
    public UserDto addNewUser(@RequestBody UserDto userDto) {
        log.info("ADMIN: Add new user {}", userDto);
        return userService.addNewUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(value = "ids", required = false, defaultValue = "new int[0]") int[] ids,
                                     @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("ADMIN: Get users with ids={}", ids);
        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("ADMIN: Delete user id={}", userId);
        userService.deleteUser(userId);
    }
}
