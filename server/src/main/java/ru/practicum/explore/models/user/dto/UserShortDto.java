package ru.practicum.explore.models.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сокращенное дто пользователя
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Имя пользователя
     */
    private String name;
}
