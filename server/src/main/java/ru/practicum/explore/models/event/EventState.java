package ru.practicum.explore.models.event;

/**
 * Состояние жизненного цикла события
 */
public enum EventState {
    /**
     * Ожидает публикации
     */
    PENDING,
    /**
     * Опубликовано
     */
    PUBLISHED,
    /**
     * Отменено
     */
    CANCELLED
}
