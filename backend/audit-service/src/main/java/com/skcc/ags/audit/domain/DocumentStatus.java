package com.skcc.ags.audit.domain;

/**
 * Enum representing the possible statuses of a security document.
 */
public enum DocumentStatus {
    SUBMITTED,    // Document has been submitted
    IN_REVIEW,    // Document is being reviewed
    APPROVED,     // Document has been approved
    REJECTED,     // Document has been rejected
    EXPIRED       // Document has expired
} 