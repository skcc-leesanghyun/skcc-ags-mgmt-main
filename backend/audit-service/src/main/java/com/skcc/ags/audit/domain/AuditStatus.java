package com.skcc.ags.audit.domain;

/**
 * Enum representing the possible statuses of an audit record.
 */
public enum AuditStatus {
    PENDING_DOCUMENTS,     // Waiting for required documents
    DOCUMENTS_COMPLETE,    // All required documents received
    IN_REVIEW,            // Documents under review
    COMPLETED,            // Audit process completed
    CANCELLED             // Audit process cancelled
} 