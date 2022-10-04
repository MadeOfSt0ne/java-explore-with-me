package ru.practicum.explore.services.admin;

import ru.practicum.explore.models.event.dto.AdminUpdateEventRequestDto;
import ru.practicum.explore.models.event.dto.EventFullDto;

import java.util.List;

/**
 * Сервис для работы с событиями
 */
public interface AdminEventService {

    /**
     * Метод возвращает полную информацию обо всех событиях подходящих под переданные условия
     */
    List<EventFullDto> getAllEvents(Long[] users, String[] states, Integer[] categories, String rangeStart,
                                    String rangeEnd, int from, int size);

    /**
     * Редактирование данных любого события администратором
     */
    EventFullDto editEvent(long eventId, AdminUpdateEventRequestDto eventRequest);

    /**
     * Публикация события
     */
    EventFullDto publishEvent(long eventId);

    /**
     * Отклонение события
     */
    EventFullDto rejectEvent(long eventId);
}
