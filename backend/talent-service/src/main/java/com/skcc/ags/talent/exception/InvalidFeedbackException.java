package com.skcc.ags.talent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when feedback data is invalid.
 * This exception will result in an HTTP 400 status code.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFeedbackException extends RuntimeException {

    private final String field;
    private final String message;
    private final String details;

    /**
     * Constructs a new InvalidFeedbackException with the specified field and message.
     *
     * @param field the name of the invalid field
     * @param message the error message
     */
    public InvalidFeedbackException(String field, String message) {
        super(String.format("Invalid feedback data - %s: %s", field, message));
        this.field = field;
        this.message = message;
        this.details = null;
    }

    /**
     * Constructs a new InvalidFeedbackException with the specified field, message, and details.
     *
     * @param field the name of the invalid field
     * @param message the error message
     * @param details additional details about the error
     */
    public InvalidFeedbackException(String field, String message, String details) {
        super(String.format("Invalid feedback data - %s: %s (%s)", field, message, details));
        this.field = field;
        this.message = message;
        this.details = details;
    }

    /**
     * Gets the name of the invalid field.
     *
     * @return the field name
     */
    public String getField() {
        return field;
    }

    /**
     * Gets the error message.
     *
     * @return the message
     */
    public String getErrorMessage() {
        return message;
    }

    /**
     * Gets additional details about the error.
     *
     * @return the details, or null if not provided
     */
    public String getDetails() {
        return details;
    }

    /**
     * Creates an instance for invalid score.
     *
     * @param scoreType the type of score that is invalid
     * @param value the invalid value
     * @return a new InvalidFeedbackException
     */
    public static InvalidFeedbackException invalidScore(String scoreType, Integer value) {
        return new InvalidFeedbackException(
            scoreType + "Score",
            "Score must be between 0 and 100",
            "Provided value: " + value
        );
    }

    /**
     * Creates an instance for missing required field.
     *
     * @param fieldName the name of the missing field
     * @return a new InvalidFeedbackException
     */
    public static InvalidFeedbackException missingRequiredField(String fieldName) {
        return new InvalidFeedbackException(
            fieldName,
            "This field is required",
            "Field cannot be null or empty"
        );
    }

    /**
     * Creates an instance for invalid field length.
     *
     * @param fieldName the name of the field with invalid length
     * @param maxLength the maximum allowed length
     * @param actualLength the actual length
     * @return a new InvalidFeedbackException
     */
    public static InvalidFeedbackException invalidLength(String fieldName, int maxLength, int actualLength) {
        return new InvalidFeedbackException(
            fieldName,
            String.format("Field length must not exceed %d characters", maxLength),
            String.format("Current length: %d", actualLength)
        );
    }

    /**
     * Creates an instance for invalid final review.
     *
     * @param reason the reason why the feedback cannot be marked as final
     * @return a new InvalidFeedbackException
     */
    public static InvalidFeedbackException invalidFinalReview(String reason) {
        return new InvalidFeedbackException(
            "finalReview",
            "Cannot mark feedback as final",
            reason
        );
    }
} 