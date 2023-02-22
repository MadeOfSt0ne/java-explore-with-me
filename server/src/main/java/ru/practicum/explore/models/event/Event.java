package ru.practicum.explore.models.event;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Formula;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Событие
 */
@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Инициатор события
     */
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    /**
     * Краткое описание
     */
    @Column(name = "annotation")
    private String annotation;
    /**
     * Категория, к которой относится данное событие
     */
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    /**
     * Полное описание события
     */
    @Column(name = "description")
    private String description;
    /**
     * Дата и время создания события
     */
    @Column(name = "created")
    private LocalDateTime createdOn = LocalDateTime.now().withNano(0);
    /**
     * Дата и время публикации события
     */
    @Column(name = "published")
    private LocalDateTime publishedOn;
    /**
     * Дата и время события
     */
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    /**
     * Нужно ли оплачивать участие
     */
    @Column(name = "paid")
    private boolean paid;
    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Column(name = "participant_limit")
    private int participantLimit;
    /**
     * Количество подтвержденных запросов на участие
     */
    @Formula("SELECT COUNT(*) FROM participation_requests r WHERE r.status = 'APPROVED' AND r.event_id = id")
    private int confirmedRequests;
    /**
     * Нужна ли пре-модерация заявок на участие
     */
    @Column(name = "request_moderation")
    private boolean requestModeration;
    /**
     * Состояние жизненного цикла события
     */
    @Enumerated(EnumType.STRING)
    private EventState eventState;
    /**
     * Заголовок
     */
    @Column(name = "title")
    private String title;
    /**
     * Долгота события (координаты)
     */
    @Column(name = "latitude")
    private float lat;
    /**
     * Широта события (координаты)
     */
    @Column(name = "longitude")
    private float lon;
    /**
     * Количество просмотров события
     */
    private int views;
    /**
     * Комментарии к событию
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @Formula("SELECT * FROM event_comments c WHERE c.event_id = id")
    private List<Comment> comments;
}
