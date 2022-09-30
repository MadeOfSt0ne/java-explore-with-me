package ru.practicum.explore.services.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.repositroy.EventRepository;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.repositroy.ParticipationRequestRepository;
import ru.practicum.explore.models.participationRequest.ParticipationRequest;
import ru.practicum.explore.models.participationRequest.RequestStatus;
import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.mappers.ParticipationRequestMapper;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repositroy.UserRepository;
import ru.practicum.explore.services.client.ParticipationRequestService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     *
     * @param userId id пользователя
     */
    @Override
    public List<ParticipationRequestDto> getOwnRequests(long userId) {
        List<ParticipationRequest> requests = requestRepository.findByRequesterId(userId);
        return ParticipationRequestMapper.toParticipationRequestDto(requests);
    }

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     *
     * @param userId id пользователя
     * @param eventId id события
     */
    @Override
    public ParticipationRequestDto addNewRequest(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        User requester = userRepository.findById(userId).orElseThrow();
        List<ParticipationRequest> requests = requestRepository.findByRequesterId(userId);
        if (requests.stream().anyMatch(r -> r.getEvent().getId() == eventId)
                || event.getInitiator().getId() == userId
                || !event.getEventState().equals(EventState.PUBLISHED)) {
            throw new IllegalStateException();
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new IllegalStateException();
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(requester);
        request.setEvent(event);
        request.setStatus(event.isRequestModeration() ? RequestStatus.PENDING : RequestStatus.CONFIRMED);
        return ParticipationRequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    /**
     * Отмена своего запроса на участие в событии
     *
     * @param userId id пользователя
     * @param requestId id запроса на участие
     */
    @Override
    public ParticipationRequestDto cancelOwnRequest(long userId, long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus(RequestStatus.CANCELLED);
        ParticipationRequest cancelled = requestRepository.save(request);
        return ParticipationRequestMapper.toParticipationRequestDto(cancelled);
    }
}
