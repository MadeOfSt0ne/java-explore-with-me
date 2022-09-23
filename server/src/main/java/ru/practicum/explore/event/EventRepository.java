package ru.practicum.explore.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    /**
     * Получение списка событий по идентификатору инициатора события
     */
    Page<Event> findByInitiatorId(long userId, Pageable pageable);

}
