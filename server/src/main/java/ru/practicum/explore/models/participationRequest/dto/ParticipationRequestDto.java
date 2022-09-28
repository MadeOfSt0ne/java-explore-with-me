package ru.practicum.explore.models.participationRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Дто запроса на участие в событии
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    /**
     * Идентификатор
     */
    private long id;
    /**
     * Дата и время создания запроса в строковом формате
     */
    private String created;
    /**
     * Идентификатор события
     */
    private long eventId;
    /**
     * Идентификатор пользователя сделавшего запрос
     */
    private long requester;
    /**
     * Статус запроса
     */
    private String status;
}
