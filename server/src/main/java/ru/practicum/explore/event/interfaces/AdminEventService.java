package ru.practicum.explore.event.interfaces;

import ru.practicum.explore.event.dto.AdminUpdateEventRequest;
import ru.practicum.explore.event.dto.EventFullDto;

import java.util.List;

public interface AdminEventService {

    /**
     * Метод возвращает полную информацию обо всех событиях подходящих под переданные условия
     */
    List<EventFullDto> getAllEvents();

    /**
     * Редактирование данных любого события администратором
     */
    EventFullDto editEvent(long eventId, AdminUpdateEventRequest eventRequest);

    /**
     * Публикация события
     */
    EventFullDto publishEvent(long eventId);

    /**
     * Отклонение события
     */
    EventFullDto rejectEvent(long eventId);
}
