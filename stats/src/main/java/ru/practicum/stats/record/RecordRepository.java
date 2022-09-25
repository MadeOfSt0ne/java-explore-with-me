package ru.practicum.stats.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, QuerydslPredicateExecutor<Record> {

    /**
     * Получение записей за определенный промежуток времени
     */
    List<Record> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Получение записей за определенный промежуток времени по выбранным uri
     */
    List<Record> findByTimestampBetweenAndUriIsIn(LocalDateTime start, LocalDateTime end, List<String> uris);
}

