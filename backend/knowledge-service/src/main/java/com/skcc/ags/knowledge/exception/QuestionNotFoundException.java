package com.skcc.ags.knowledge.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException(Long id) {
        super("Question not found with id: " + id);
    }
} 