package ru.practicum.explore.exceptions;

/**
 * Исключение, выбрасываемое при получении невалидных данных
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
