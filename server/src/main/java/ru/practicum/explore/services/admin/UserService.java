package ru.practicum.explore.services.admin;

import ru.practicum.explore.models.user.dto.UserDto;

import java.util.List;

/**
 * Сервис для работы с пользователями
 */
public interface UserService {

    /**
     * Получение списка пользователей
     */
    List<UserDto> getUsers(Long[] ids, int from, int size);

    /**
     * Добавление нового пользователя
     */
    UserDto addNewUser(UserDto userDto);

    /**
     * Удаление пользователя по id
     */
    void deleteUser(long id);

    /**
     * Запретить пользователю оставлять комментарии
     */
    void ban(long userId);

    /**
     * Разрешить пользователю оставлять комментарии
     */
    void unban(long userId);
}
