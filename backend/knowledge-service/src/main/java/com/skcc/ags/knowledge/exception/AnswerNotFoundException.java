package com.skcc.ags.knowledge.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException(Long id) {
        super("Answer not found with id: " + id);
    }
} 