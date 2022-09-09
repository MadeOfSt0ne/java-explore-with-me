package ru.practicum.explore.event;

import lombok.*;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.user.User;

import javax.persistence.*;

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
    private String createdOn;
    @Column(name = "published")
    private String publishedOn;
    @Column(name = "event_date")
    private String eventDate;
    @Column(name = "paid")
    private boolean paid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "request_moderation")
    private boolean requestModeration;
    @Column(name = "state")
    private EventState eventState;
    @Column(name = "title")
    private String title;
    @Column(name = "latitude")
    private float lat;
    @Column(name = "longitude")
    private float lon;
}
