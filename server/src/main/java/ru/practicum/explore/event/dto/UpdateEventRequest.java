package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private String annotation;
    private Long categoryId;
    private String description;
    private String eventDate;
    private long eventId;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}
