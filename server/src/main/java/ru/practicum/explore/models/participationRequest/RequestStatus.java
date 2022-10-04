package ru.practicum.explore.models.participationRequest;

/**
 * Статус запроса
 */
public enum RequestStatus {
    /**
     * Ожидает ответа
     */
    PENDING,
    /**
     * Подтвержден
     */
    CONFIRMED,
    /**
     * Отменен
     */
    CANCELLED,
    /**
     * Отказано
     */
    REJECTED
}
