package ru.practicum.explore.event.dto;

import lombok.NoArgsConstructor;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventState;
import ru.practicum.explore.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class EventMapper {

    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                new EventFullDto.CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getCreatedOn().toString(),
                event.getDescription(),
                event.getEventDate().toString(),
                event.getId(),
                new EventFullDto.UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getConfirmedRequests(),
                event.getPublishedOn() == null ? "Not published yet" : event.getPublishedOn().toString(),
                event.isRequestModeration(),
                event.getEventState().toString(),
                event.getTitle(),
                event.getViews(),
                new EventFullDto.Location(event.getLat(), event.getLon())
        );
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getAnnotation(),
                new EventShortDto.CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                event.getEventDate().toString(),
                event.getId(),
                new EventShortDto.UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public static Event toEvent(NewEventDto newEventDto, User user, Category category) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Event event = new Event();
        event.setInitiator(user);
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), formatter));
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(true);
        event.setEventState(EventState.PENDING);
        event.setTitle(newEventDto.getTitle());
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        return event;
    }

    public static Event toUpdatedEvent(AdminUpdateEventRequest dto, Category category, Event event) {
        event.setAnnotation(dto.getAnnotation());
        event.setCategory(category);
        event.setDescription(dto.getDescription());
        event.setEventDate(LocalDateTime.parse(dto.getEventDate()));
        event.setPaid(dto.isPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.isRequestModeration());
        event.setTitle(dto.getTitle());
        event.setLat(dto.getLocation().getLat());
        event.setLon(dto.getLocation().getLon());
        return event;
    }
}
