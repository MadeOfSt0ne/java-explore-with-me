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
    private long categoryId;
    private String description;
    private String eventDate;
    private long eventId;
    private boolean paid;
    private int participantLimit;
    private String title;
}
