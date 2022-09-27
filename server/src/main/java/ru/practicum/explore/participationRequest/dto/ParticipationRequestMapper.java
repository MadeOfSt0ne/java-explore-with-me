package ru.practicum.explore.participationRequest.dto;

import lombok.NoArgsConstructor;
import ru.practicum.explore.participationRequest.ParticipationRequest;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class ParticipationRequestMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(participationRequest.getId(),
                                           participationRequest.getCreated().format(FORMATTER),
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
