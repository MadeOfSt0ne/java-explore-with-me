package ru.practicum.explore.services.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.EventMapper;
import ru.practicum.explore.mappers.ParticipationRequestMapper;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.category.CategoryRepository;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventRepository;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.models.event.dto.EventShortDto;
import ru.practicum.explore.models.event.dto.NewEventDto;
import ru.practicum.explore.models.event.dto.UpdateEventRequestDto;
import ru.practicum.explore.models.participationRequest.ParticipationRequest;
import ru.practicum.explore.models.participationRequest.ParticipationRequestRepository;
import ru.practicum.explore.models.participationRequest.RequestStatus;
import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.models.user.UserRepository;
import ru.practicum.explore.utils.RestTemplateClient.ViewsProcessor;

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
    private final ViewsProcessor viewsProcessor;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Получение событий, добавленных текущим пользователем
     *
     * @param userId id пользователя
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     */
    @Override
    public List<EventShortDto> getOwnEvents(long userId, int from, int size) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Pageable pageable = PageRequest.of(from, size);
        Page<Event> events = eventRepository.findByInitiatorId(userId, pageable);
        events.forEach(e -> e.setViews(viewsProcessor.getViews(e.getId())));
        List<EventShortDto> dtos = new ArrayList<>();
        for (Event event : events) {
            dtos.add(EventMapper.toEventShortDto(event));
        }
        return dtos;
    }

    /**
     * Изменение события добавленного текущим пользователем
     *
     * @param userId id пользователя
     * @param updateEventRequestDto dto события
     * @throws IllegalStateException если событие уже опубликовано или время начала раньше, чем через 2 часа от
     * текущего момента
     */
    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequestDto updateEventRequestDto) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(updateEventRequestDto.getEventId()).orElseThrow();
        if (event.getEventState().equals(EventState.PUBLISHED) ||
                event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalStateException();
        }
        if (updateEventRequestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateEventRequestDto.getCategoryId()).orElseThrow();
            event.setCategory(category);
        }
        if (updateEventRequestDto.getAnnotation() != null) {
            event.setAnnotation(updateEventRequestDto.getAnnotation());
        }
        if (updateEventRequestDto.getDescription() != null) {
            event.setDescription(updateEventRequestDto.getDescription());
        }
        if (updateEventRequestDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEventRequestDto.getEventDate(), FORMATTER));
        }
        if (updateEventRequestDto.getPaid() != null) {
            event.setPaid(updateEventRequestDto.getPaid());
        }
        if (updateEventRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventRequestDto.getParticipantLimit());
        }
        if (updateEventRequestDto.getTitle() != null) {
            event.setTitle(updateEventRequestDto.getTitle());
        }
        if (event.getEventState().equals(EventState.CANCELLED)) {
            event.setEventState(EventState.PENDING);
        }
        Event updated = eventRepository.save(event);
        return EventMapper.toEventFullDto(updated);
    }

    /**
     * Добавление нового события
     *
     * @param userId id пользователя
     * @param newEventDto dto события
     * @throws IllegalStateException если время начала события раньше, чем через 2 часа от текущего момента
     */
    @Override
    public EventFullDto addNewEvent(long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate() == null) {
            throw new IllegalStateException();
        }
        User initiator = userRepository.findById(userId).orElseThrow();
        Category category = categoryRepository.findById(newEventDto.getCategoryId()).orElseThrow();
        Event newEvent = eventRepository.save(EventMapper.toEvent(newEventDto, initiator, category));
        if (newEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalStateException();
        }
        return EventMapper.toEventFullDto(newEvent);
    }

    /**
     * Получение полной информации о событии добавленном текущим пользователем
     *
     * @param userId id пользователя
     * @param eventId id события
     * @throws IllegalStateException если пользователь не является инициатором события
     */
    @Override
    public EventFullDto getEvent(long userId, long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (event.getInitiator().getId() != userId) {
            throw new IllegalStateException();
        }
        event.setViews(viewsProcessor.getViews(eventId));
        return EventMapper.toEventFullDto(event);
    }

    /**
     * Отмена события добавленного пользователем
     *
     * @param userId id пользователя
     * @param eventId id события
     * @throws IllegalStateException если событие уже опубликовано или отменено
     * или если пользователь не является инициатором события
     */
    @Override
    public EventFullDto cancelEvent(long userId, long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (event.getInitiator().getId() != userId) {
            throw new IllegalStateException();
        }
        if (!event.getEventState().equals(EventState.PENDING)) {
            throw new IllegalStateException();
        }
        event.setEventState(EventState.CANCELLED);
        event.setViews(viewsProcessor.getViews(eventId));
        Event cancelled = eventRepository.save(event);
        return EventMapper.toEventFullDto(cancelled);
    }

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     *
     * @param userId id пользователя
     * @param eventId id события
     * @throws IllegalStateException если пользователь не является инициатором события
     */
    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (initiator.getId() != event.getInitiator().getId()) {
            throw new IllegalStateException();
        }
        List<ParticipationRequest> requests = requestRepository.findByEventId(eventId);
        return ParticipationRequestMapper.toParticipationRequestDto(requests);
    }

    /**
     * Подтверждение чужой заявки на участие в событии текущего пользователя
     *
     * @param userId id инициатора события
     * @param eventId id события
     * @param requestId id запроса на участие в событии
     * @throws IllegalStateException если пользователь не является инициатором события
     */
    @Override
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long requestId) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (initiator.getId() != event.getInitiator().getId()) {
            throw new IllegalStateException();
        }
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() == event.getParticipantLimit()) {
            throw new IllegalStateException();
        }
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestStatus.CONFIRMED);
        ParticipationRequest confirmed = requestRepository.save(request);
        // Если лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            List<ParticipationRequest> requests = requestRepository.findByEventId(eventId);
            requests.forEach(r -> r.setStatus(RequestStatus.CANCELLED));
        }
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
        User initiator = userRepository.findById(userId).orElseThrow();
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestStatus.REJECTED);
        ParticipationRequest rejected = requestRepository.save(request);
        return ParticipationRequestMapper.toParticipationRequestDto(rejected);
    }
}
