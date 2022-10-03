package ru.practicum.stats.models.record;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Запись(объект) статистики
 */
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
    /**
     * Название приложения сделавшего запрос
     */
    @Column(name = "app")
    private String app;

    /**
     * Ip адрес, с которого сделан запрос
     */
    @Column(name = "ip")
    private String ip;

    /**
     * Время создания записи
     */
    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now().withNano(0);

    /**
     * Адрес, по которому сделан запрос
     */
    @Column(name = "uri")
    private String uri;
}
