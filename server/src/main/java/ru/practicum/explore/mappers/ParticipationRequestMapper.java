package ru.practicum.explore.mappers;

import lombok.NoArgsConstructor;
import ru.practicum.explore.models.participationRequest.ParticipationRequest;
import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ru.practicum.explore.utils.DateTimeFormat.DateTimeFormat.FORMATTER;

/**
 * Маппер для заявок на участие в событии
 */
@NoArgsConstructor
public class ParticipationRequestMapper {

    /**
     * Создание дто из заявки
     */
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(participationRequest.getId(),
                                           participationRequest.getCreated().format(FORMATTER),
                                           participationRequest.getEvent().getId(),
                                           participationRequest.getRequester().getId(),
                                           participationRequest.getStatus().toString());
    }

    /**
     * Создание списка дто из списка заявок
     */
    public static List<ParticipationRequestDto> toParticipationRequestDto(Collection<ParticipationRequest> list) {
        List<ParticipationRequestDto> dtos = new ArrayList<>();
        for (ParticipationRequest request : list) {
            dtos.add(toParticipationRequestDto(request));
        }
        return dtos;
    }

}
