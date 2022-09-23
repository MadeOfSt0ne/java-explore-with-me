package ru.practicum.explore.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        return new ErrorResponse(e.getLocalizedMessage(), "For the requested operation the conditions are not met.",
                ErrorStatus._400_BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return new ErrorResponse(e.getLocalizedMessage(), "The required object was not found.",
                ErrorStatus._404_NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return new ErrorResponse(e.getLocalizedMessage(), "Integrity constraint has been violated.",
                ErrorStatus._409_CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIllegalStateException(final IllegalStateException e) {
        return new ErrorResponse(e.getMessage(), "For the requested operation the conditions are not met.",
                ErrorStatus._403_FORBIDDEN);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public ErrorResponse handleDateTimeParseException(final DateTimeParseException e) {
        return new ErrorResponse(e.getMessage(), "Wrong datetime format.", ErrorStatus._418_I_AM_A_TEAPOT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(final Throwable e) {
        return new ErrorResponse(e.getMessage(), "Error occurred.", ErrorStatus._500_INTERNAL_SERVER_ERROR);
    }

}
