package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.interfaces.AdminEventService;
import ru.practicum.explore.event.interfaces.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;

    /**
     * Метод возвращает полную информацию обо всех событиях подходящих под переданные условия
     */
    @Override
    public List<EventFullDto> getAllEvents() {
        return null;
    }

    /**
     * Редактирование данных любого события администратором
     *
     * @param eventId id события
     * @param eventRequest dto события
     */
    @Override
    public EventFullDto editEvent(long eventId, AdminUpdateEventRequest eventRequest) {
        return null;
    }

    /**
     * Публикация события
     *
     * @param eventId id события
     */
    @Override
    public EventFullDto publishEvent(long eventId) {
        return null;
    }

    /**
     * Отклонение события
     *
     * @param eventId id события
     */
    @Override
    public EventFullDto rejectEvent(long eventId) {
        return null;
    }
}
