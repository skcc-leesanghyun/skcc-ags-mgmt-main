package com.skcc.ags.planning.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlanningException.class)
    public ResponseEntity<ErrorResponse> handlePlanningException(PlanningException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            ex.getStatus(),
            "Planning Error",
            ex.getMessage(),
            request.getDescription(false)
        );
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        
        ErrorResponse errorResponse = new ErrorResponse(
            400,
            "Validation Error",
            "Invalid input data",
            request.getDescription(false)
        );

        for (FieldError fieldError : fieldErrors) {
            errorResponse.addValidationError(
                fieldError.getField(),
                fieldError.getDefaultMessage()
            );
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
            500,
            "Internal Server Error",
            ex.getMessage(),
            request.getDescription(false)
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }
} 