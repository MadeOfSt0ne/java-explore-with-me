package ru.practicum.explore.participationRequest;

import lombok.*;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.user.User;

import javax.persistence.*;

@Entity
@Table(name = "participation_requests")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Column(name = "created")
    private String created;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
