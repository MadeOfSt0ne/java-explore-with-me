package ru.practicum.explore.participationRequest.dto;

import lombok.NoArgsConstructor;
import ru.practicum.explore.participationRequest.ParticipationRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class ParticipationRequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(participationRequest.getId(),
                                           participationRequest.getCreated(),
                                           participationRequest.getEvent().getId(),
                                           participationRequest.getRequester().getId(),
                                           participationRequest.getStatus().toString());
    }

    public static List<ParticipationRequestDto> toParticipationRequestDto(Collection<ParticipationRequest> list) {
        List<ParticipationRequestDto> dtos = new ArrayList<>();
        for (ParticipationRequest request : list) {
            dtos.add(toParticipationRequestDto(request));
        }
        return dtos;
    }

}
