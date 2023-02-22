package ru.practicum.explore.services.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.UserMapper;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.models.user.dto.UserDto;
import ru.practicum.explore.repository.UserRepository;
import ru.practicum.explore.services.admin.UserService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Получение списка пользователей
     *
     * @param from количество элементов, которые нужно пропустить для формирования текущего списка
     * @param size количество элементов в списке
     */
    @Override
    public List<UserDto> getUsers(Long[] ids, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<User> users;
        if (ids != null) {
            List<Long> userIds = Arrays.asList(ids);
            users = userRepository.findUsersByIdIn(userIds, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return UserMapper.toUserDto(users);
    }

    /**
     * Добавление нового пользователя
     *
     * @param userDto dto пользователя
     */
    @Override
    public UserDto addNewUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    /**
     * Удаление пользователя по id
     *
     * @param id id пользователя
     */
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Запретить пользователю оставлять комментарии. По умолчанию всем разрешено оставлять комментарии
     *
     * @param userId id пользователя
     */
    @Override
    public void ban(long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setBanned(true);
        userRepository.save(user);
    }

    /**
     * Разрешить пользователю оставлять комментарии
     *
     * @param userId id пользователя
     */
    @Override
    public void unban(long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setBanned(false);
        userRepository.save(user);
    }
}
