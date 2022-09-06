package ru.practicum.explore.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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

    @PatchMapping("/{userId}/activate")
    public UserDto activateUser(@PathVariable long userId) {
        log.info("ADMIN: Activate user id={}", userId);
        return userService.updateUser(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("ADMIN: Get all users");
        return userService.getUsers(from, size);
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable long userId) {
        log.info("ADMIN: Get user id={}", userId);
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("ADMIN: Delete user id={}", userId);
        userService.deleteUser(userId);
    }
}
