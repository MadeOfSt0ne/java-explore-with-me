package ru.practicum.explore.user.service;

import ru.practicum.explore.user.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Получение списка пользователей
     */
    List<UserDto> getUsers(int[] ids, int from, int size);

    /**
     * Добавление нового пользователя
     */
    UserDto addNewUser(UserDto userDto);

    /**
     * Удаление пользователя по id
     */
    void deleteUser(long id);
}
