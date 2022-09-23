package ru.practicum.explore.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private int views;

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
}