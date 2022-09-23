package ru.practicum.explore.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private List<String> errors;
    private final String message;
    private final String reason;
    private final ErrorStatus status;
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
