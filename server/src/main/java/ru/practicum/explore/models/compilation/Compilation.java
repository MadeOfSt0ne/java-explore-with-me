package ru.practicum.explore.models.compilation;

import lombok.*;
import ru.practicum.explore.models.event.Event;

import javax.persistence.*;
import java.util.Set;

/**
 * Подборка событий
 */
@Entity
@Table(name = "compilations")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Закреплена ли категория на главной странице
     */
    @Column(name = "pinned")
    private boolean pinned;
    /**
     * Название категории
     */
    @Column(name = "title")
    private String title;
    /**
     * Список событий, относящихся к данной категории
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinTable(name = "event_compilation",
            joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private Set<Event> events;
}
