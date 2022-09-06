package ru.practicum.explore.user;

import ru.practicum.explore.user.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Получение списка пользователей
     */
    List<UserDto> getUsers(int from, int size);

    /**
     * Добавление нового пользователя
     */
    UserDto addNewUser(UserDto userDto);

    /**
     * Активация пользователя
     */
    UserDto updateUser(long userId);

    /**
     * Поиск пользователя по id
     */
    UserDto findById(long id);

    /**
     * Удаление пользователя по id
     */
    void deleteUser(long id);
}
