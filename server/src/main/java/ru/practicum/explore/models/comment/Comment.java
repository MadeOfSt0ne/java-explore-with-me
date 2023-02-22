package ru.practicum.explore.models.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Комментарий к событию
 */
@Entity
@Table(name = "event_comments")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Текст комментария
     */
    @Column(name = "text")
    private String text;

    /**
     * Автор комментария
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * Событие, к которому относится комментарий
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    /**
     * Дата и время создания комментария
     */
    @Column(name = "createdOn")
    private LocalDateTime createdOn = LocalDateTime.now().withNano(0);

    /**
     * Дата и время редактирования комментария
     */
    @Column(name = "editedOn")
    private LocalDateTime editedOn;
}
