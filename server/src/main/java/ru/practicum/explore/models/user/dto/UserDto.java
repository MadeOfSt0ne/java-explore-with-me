package ru.practicum.explore.models.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто пользователя сервиса
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Имя пользователя
     */
    private String name;
    /**
     * Электронная почта пользователя
     */
    private String email;
    /**
     * Может ли пользователь оставлять комментарии
     */
    private boolean banned;
}
