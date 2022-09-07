package ru.practicum.explore.participationRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private long id;
    private String created;
    private long eventId;
    private long requester;
    private String status;
}
