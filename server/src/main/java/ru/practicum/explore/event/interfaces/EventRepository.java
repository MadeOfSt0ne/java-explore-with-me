package ru.practicum.explore.event.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore.event.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByInitiatorId(long userId, Pageable pageable);

}
