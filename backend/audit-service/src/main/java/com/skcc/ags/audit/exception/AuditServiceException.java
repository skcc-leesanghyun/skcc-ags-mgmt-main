package com.skcc.ags.audit.exception;

/**
 * Base exception class for audit service specific exceptions.
 */
public class AuditServiceException extends RuntimeException {
    
    public AuditServiceException(String message) {
        super(message);
    }

    public AuditServiceException(String message, Throwable cause) {
        super(message, cause);
    }
} 