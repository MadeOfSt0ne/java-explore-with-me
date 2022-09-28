package ru.practicum.explore.mappers;

import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.models.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Маппер для пользователей сервиса
 */
@NoArgsConstructor
public class UserMapper {

    /**
     * Создание дто из пользователя
     */
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    /**
     * Создание нового пользователя
     */
    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    /**
     * Создание списка дто из списка пользователей
     */
    public static List<UserDto> toUserDto(Page<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(toUserDto(user));
        }
        return dtos;
    }
}
