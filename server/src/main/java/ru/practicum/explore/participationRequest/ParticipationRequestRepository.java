package ru.practicum.explore.participationRequest;

import org.springframework.data.jpa.repository.JpaRepository;

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
     * Получение информации о подтвержденных запросах на участие в событии
     */
    List<ParticipationRequest> findByEventIdAndStatus(long event_id, RequestStatus status);
}
