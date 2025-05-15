package com.skcc.ags.planning.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String type;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private List<ValidationError> validationErrors;

    public ErrorResponse(int status, String type, String message, String path) {
        this.status = status;
        this.type = type;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public void addValidationError(String field, String message) {
        if (validationErrors == null) {
            validationErrors = new ArrayList<>();
        }
        validationErrors.add(new ValidationError(field, message));
    }

    @Getter
    @Setter
    private static class ValidationError {
        private final String field;
        private final String message;

        ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
} 