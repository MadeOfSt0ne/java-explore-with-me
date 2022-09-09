package ru.practicum.explore.event.dto;

import lombok.NoArgsConstructor;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventState;
import ru.practicum.explore.user.User;

@NoArgsConstructor
public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                new EventFullDto.CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                new EventFullDto.UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getEventState().toString(),
                event.getTitle(),
                0,
                new EventFullDto.Location(event.getLat(), event.getLon())
        );
    }

    public static EventShortDto toEventShortDto(Event event, int confirmedRequests) {
        return new EventShortDto(
                event.getAnnotation(),
                new EventShortDto.CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                confirmedRequests,
                event.getEventDate(),
                event.getId(),
                new EventShortDto.UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.isPaid(),
                event.getTitle(),
                0
        );
    }

    public static Event toEvent(NewEventDto newEventDto, User user, Category category, String created) {
        return new Event(
                0,
                user,
                newEventDto.getAnnotation(),
                category,
                newEventDto.getDescription(),
                created,
                "NOT PUBLISHED",
                newEventDto.getEventDate(),
                newEventDto.isPaid(),
                newEventDto.getParticipantLimit(),
                true,
                EventState.PENDING,
                newEventDto.getTitle(),
                newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon()
        );
    }

    public static Event toUpdatedEvent(AdminUpdateEventRequest dto, Category category, Event event) {
        event.setAnnotation(dto.getAnnotation());
        event.setCategory(category);
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setPaid(dto.isPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.isRequestModeration());
        event.setTitle(dto.getTitle());
        event.setLat(dto.getLocation().getLat());
        event.setLon(dto.getLocation().getLon());
        return event;
    }
}
