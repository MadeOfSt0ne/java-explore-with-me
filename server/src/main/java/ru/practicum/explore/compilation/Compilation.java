package ru.practicum.explore.compilation;

import lombok.*;
import ru.practicum.explore.event.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "pinned")
    private boolean pinned;
    @Column(name = "title")
    private String title;
    @OneToMany
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Set<Event> events;
}
