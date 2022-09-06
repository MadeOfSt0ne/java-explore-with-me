package ru.practicum.explore.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.user.dto.UserDto;
import ru.practicum.explore.user.dto.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Получение списка пользователей
     *
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     */
    @Override
    public List<UserDto> getUsers(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<User> users = userRepository.findAll(pageable);
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
     * Активация пользователя
     *
     * @param userId id пользователя
     */
    @Override
    public UserDto updateUser(long userId) {
        User updated = userRepository.findById(userId).orElseThrow();
        userRepository.save(updated);
        return UserMapper.toUserDto(updated);
    }

    /**
     * Поиск пользователя по id
     *
     * @param id id пользователя
     */
    @Override
    public UserDto findById(long id) {
        User user = userRepository.findById(id).orElseThrow();
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
}
