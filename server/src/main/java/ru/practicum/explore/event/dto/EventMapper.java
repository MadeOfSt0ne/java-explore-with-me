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
                0
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
                newEventDto.getTitle()
        );
    }
}
