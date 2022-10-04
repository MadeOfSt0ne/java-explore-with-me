package ru.practicum.explore.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель данных исключения
 */
@Data
public class ErrorResponse {
    /**
     * Список ошибок
     */
    private List<String> errors;
    /**
     * Сообщение ошибки
     */
    private final String message;
    /**
     * Причина ошибки
     */
    private final String reason;
    /**
     * Статус ошибки
     */
    private final ErrorStatus status;
    /**
     * Время возникновения ошибки
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(String message, String reason, ErrorStatus status) {
        this.message = message;
        this.reason = reason;
        this.status = status;
    }

    public ErrorResponse(List<String> errors, String message, String reason, ErrorStatus status) {
        this.errors = errors;
        this.message = message;
        this.reason = reason;
        this.status = status;
    }
}
