package ru.practicum.explore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.models.participationRequest.ParticipationRequest;
import ru.practicum.explore.models.participationRequest.RequestStatus;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    List<ParticipationRequest> findByRequesterId(long userId);

    /**
     * Получение информации о запросах на участие в событии
     */
    List<ParticipationRequest> findByEventId(long eventId);

    /**
     * Получение списка подтвержденных запросов на участие в событии
     */
    List<ParticipationRequest> findByEventIdAndStatus(long eventId, RequestStatus status);

    /**
     * Поиск запроса на участие по идентификатору пользователя, сделавшего запрос, и идентификатору события
     */
    ParticipationRequest findParticipationRequestByRequesterIdAndEventId(long requesterId, long eventId);
}
