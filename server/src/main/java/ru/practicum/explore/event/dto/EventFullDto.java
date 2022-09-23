package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private String createdOn;
    private String description;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private boolean paid;
    private int participantLimit;
    private int confirmedRequests;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private int views;
    private Location location;

    @Data
    @AllArgsConstructor
    static class CategoryDto {
        private long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UserShortDto {
        private long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Location {
        private float lat;
        private float lon;
    }
}
