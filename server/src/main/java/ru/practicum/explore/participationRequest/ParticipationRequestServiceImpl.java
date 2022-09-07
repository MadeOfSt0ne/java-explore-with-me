package ru.practicum.explore.participationRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.interfaces.EventRepository;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestMapper;
import ru.practicum.explore.user.User;
import ru.practicum.explore.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        User user = userRepository.findById(userId).orElseThrow();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String created = LocalDateTime.now().format(formatter);
        ParticipationRequest request = requestRepository.save(
                new ParticipationRequest(0, event, user, created, RequestStatus.PENDING));
        return ParticipationRequestMapper.toParticipationRequestDto(request);
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
