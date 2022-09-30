package ru.practicum.explore.repositroy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explore.models.event.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    /**
     * Получение списка событий по идентификатору инициатора события
     */
    Page<Event> findByInitiatorId(long userId, Pageable pageable);

    /**
     * Поиск событий относящихся к определенной категории
     */
    List<Event> findAllByCategoryId(long catId);

}
