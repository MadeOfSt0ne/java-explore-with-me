package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.category.CategoryRepository;
import ru.practicum.explore.event.dto.*;
import ru.practicum.explore.event.interfaces.EventRepository;
import ru.practicum.explore.event.interfaces.PrivateEventService;
import ru.practicum.explore.participationRequest.ParticipationRequest;
import ru.practicum.explore.participationRequest.ParticipationRequestRepository;
import ru.practicum.explore.participationRequest.RequestStatus;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestMapper;
import ru.practicum.explore.user.User;
import ru.practicum.explore.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository requestRepository;

    /**
     * Получение событий, добавленных текущим пользователем
     *
     * @param userId id пользователя
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     */
    @Override
    public List<EventShortDto> getOwnEvents(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Event> events = eventRepository.findByInitiatorId(userId, pageable);
        List<EventShortDto> dtos = new ArrayList<>();
        int confirmed;
        for (Event event : events) {
            confirmed = requestRepository.findByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED).size();
            dtos.add(EventMapper.toEventShortDto(event, confirmed));
        }
        return dtos;
    }

    /**
     * Изменение события добавленного текущим пользователем
     *
     * @param userId id пользователя
     * @param updateEventRequest dto события
     */
    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(updateEventRequest.getEventId()).orElseThrow();
        event.setAnnotation(updateEventRequest.getAnnotation());
        Category category = categoryRepository.findById(updateEventRequest.getCategoryId()).orElseThrow();
        event.setCategory(category);
        event.setDescription(updateEventRequest.getDescription());
        event.setEventDate(updateEventRequest.getEventDate());
        event.setPaid(updateEventRequest.isPaid());
        event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        event.setTitle(updateEventRequest.getTitle());
        Event updated = eventRepository.save(event);
        return EventMapper.toEventFullDto(updated);
    }

    /**
     * Добавление нового события
     *
     * @param userId id пользователя
     * @param newEventDto dto события
     */
    @Override
    public EventFullDto addNewEvent(long userId, NewEventDto newEventDto) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Category category = categoryRepository.findById(newEventDto.getCategoryId()).orElseThrow();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String created = LocalDateTime.now().format(formatter);
        Event event = EventMapper.toEvent(newEventDto, initiator, category, created);
        Event newEvent = eventRepository.save(event);
        return EventMapper.toEventFullDto(newEvent);
    }

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     *
     * @param userId id пользователя
     * @param eventId id события
     */
    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Отмена события добавленного пользователем
     *
     * @param userId id пользователя
     * @param eventId id события
     */
    @Override
    public EventFullDto cancelEvent(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setEventState(EventState.CANCELLED);
        Event cancelled = eventRepository.save(event);
        return EventMapper.toEventFullDto(cancelled);
    }

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     *
     * @param userId id пользователя
     * @param eventId id события
     */
    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        List<ParticipationRequest> requests = requestRepository.findByEventId(eventId);
        return ParticipationRequestMapper.toParticipationRequestDto(requests);
    }

    /**
     * Подтверждение чужой заявки на участие в событии текущего пользователя
     *
     * @param userId id пользователя
     * @param eventId id события
     * @param requestId id запроса на участие в событии
     */
    @Override
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestStatus.CONFIRMED);
        ParticipationRequest confirmed = requestRepository.save(request);
        return ParticipationRequestMapper.toParticipationRequestDto(confirmed);
    }

    /**
     * Отклонение чужой заявки на участие в событии текущего пользователя
     *
     * @param userId id пользователя
     * @param eventId id события
     * @param requestId id запроса на участие в событии
     */
    @Override
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestStatus.REJECTED);
        ParticipationRequest rejected = requestRepository.save(request);
        return ParticipationRequestMapper.toParticipationRequestDto(rejected);
    }
}
