package ru.practicum.explore.models.participationRequest;

import lombok.*;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Запрос на участие в событии
 */
@Entity
@Table(name = "participation_requests")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequest {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Событие, на участие в котором сделан запрос
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    /**
     * Пользователь, сделавший запрос на участие
     */
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    /**
     * Дата и время создания запроса
     */
    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now().withNano(0);
    /**
     * Статус запроса
     */
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
