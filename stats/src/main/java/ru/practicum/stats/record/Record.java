package ru.practicum.stats.record;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "records")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "app")
    private String app;
    @Column(name = "ip")
    private String ip;
    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now().withNano(0);
    @Column(name = "uri")
    private String uri;
}
