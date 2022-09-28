package ru.practicum.explore.mappers;

import lombok.NoArgsConstructor;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.event.dto.AdminUpdateEventRequestDto;
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.models.event.dto.EventShortDto;
import ru.practicum.explore.models.event.dto.NewEventDto;
import ru.practicum.explore.models.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Маппер для событий
 */
@NoArgsConstructor
public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Создание полного дто из события
     */
    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation() == null ? "no annotation" : event.getAnnotation(),
                new EventFullDto.CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                event.getCreatedOn() == null ? "null" : event.getCreatedOn().format(FORMATTER),
                event.getDescription() == null ? "no description" : event.getDescription(),
                event.getEventDate() == null ? "null" : event.getEventDate().format(FORMATTER),
                event.getId(),
                new EventFullDto.UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getConfirmedRequests(),
                event.getPublishedOn() == null ? "Not published yet" : event.getPublishedOn().format(FORMATTER),
                event.isRequestModeration(),
                event.getEventState().toString(),
                event.getTitle() == null ? "no title" : event.getTitle(),
                event.getViews(),
                new EventFullDto.Location(event.getLat(), event.getLon())
        );
    }

    /**
     * Создание сокращенного дто из события
     */
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

    /**
     * Создание нового события из дто, пользователя-инициатора и категории, к которой относится данное событие
     */
    public static Event toEvent(NewEventDto newEventDto, User initiator, Category category) {
        Event event = new Event();
        event.setInitiator(initiator);
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), FORMATTER));
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(true);
        event.setEventState(EventState.PENDING);
        event.setTitle(newEventDto.getTitle());
        event.setLat(newEventDto.getLocation().getLat());
        event.setLon(newEventDto.getLocation().getLon());
        return event;
    }

    /**
     * Обновление события на основе дто и категории
     */
    public static Event toUpdatedEvent(AdminUpdateEventRequestDto dto, Category category, Event event) {
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (category != null) {
            event.setCategory(category);
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(dto.getEventDate(), FORMATTER));
        }
        event.setPaid(dto.isPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.isRequestModeration());
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        if (dto.getLocation() != null) {
            event.setLat(dto.getLocation().getLat());
            event.setLon(dto.getLocation().getLon());
        }
        return event;
    }
}
