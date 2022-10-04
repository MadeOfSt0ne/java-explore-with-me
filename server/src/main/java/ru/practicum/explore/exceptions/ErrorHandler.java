package ru.practicum.explore.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

/**
 * Контроллер для обработки исключений
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Невалидные данные
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        return new ErrorResponse(e.getLocalizedMessage(), "For the requested operation the conditions are not met.",
                ErrorStatus._400_BAD_REQUEST);
    }

    /**
     * Объект не найден
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NoSuchElementException e) {
        return new ErrorResponse(e.getLocalizedMessage(), "The required object was not found.",
                ErrorStatus._404_NOT_FOUND);
    }

    /**
     * Объект уже есть в базе данных
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return new ErrorResponse(e.getLocalizedMessage(), "Integrity constraint has been violated.",
                ErrorStatus._409_CONFLICT);
    }

    /**
     * Нет прав на выполнение данной операции
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIllegalStateException(final IllegalStateException e) {
        return new ErrorResponse(e.getMessage(), "For the requested operation the conditions are not met.",
                ErrorStatus._403_FORBIDDEN);
    }

    /**
     * Неверный формат даты и времени
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public ErrorResponse handleDateTimeParseException(final DateTimeParseException e) {
        return new ErrorResponse(e.getMessage(), "Wrong datetime format. Use yyyy-MM-dd HH:mm:ss instead.",
                ErrorStatus._418_I_AM_A_TEAPOT);
    }

    /**
     * Внутренняя ошибка сервера
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(final Throwable e) {
        return new ErrorResponse(e.getMessage(), "Error occurred.", ErrorStatus._500_INTERNAL_SERVER_ERROR);
    }

}
