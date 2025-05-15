package com.skcc.ags.audit.domain;

/**
 * Enum representing the types of security documents required in the audit process.
 */
public enum SecurityDocumentType {
    SECURITY_PLEDGE,          // 보안서약서
    NDA,                      // Non-Disclosure Agreement
    COMPLIANCE_CERTIFICATE,   // Compliance certification
    ACCESS_REQUEST,           // System access request form
    OTHER                     // Other security-related documents
} 