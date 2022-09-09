package ru.practicum.explore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private String reason;
    private ErrorStatus status;
    private String timestamp;
}
