package ru.practicum.explore.event;

import lombok.*;
import org.hibernate.annotations.Formula;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @Column(name = "annotation")
    private String annotation;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "description")
    private String description;
    @Column(name = "created")
    private LocalDateTime createdOn = LocalDateTime.now().withNano(0);
    @Column(name = "published")
    private LocalDateTime publishedOn;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "paid")
    private boolean paid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Formula("SELECT COUNT(*) FROM participation_requests r WHERE r.status = 'APPROVED' AND r.event_id = id")
    private int confirmedRequests;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventState eventState;
    @Column(name = "title")
    private String title;
    @Column(name = "latitude")
    private float lat;
    @Column(name = "longitude")
    private float lon;
    private int views;
}
