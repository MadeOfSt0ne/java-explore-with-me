package ru.practicum.stats.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long>, QuerydslPredicateExecutor<Record> {

    List<Record> findByTimestampBetweenAndUriIsIn(LocalDateTime start, LocalDateTime end, List<String> uri);
}

