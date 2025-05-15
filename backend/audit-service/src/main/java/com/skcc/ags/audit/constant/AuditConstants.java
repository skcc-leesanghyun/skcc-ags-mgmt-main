package com.skcc.ags.audit.constant;

public class AuditConstants {
    // TODO: 상수 정의
    public static final String DOCUMENT_UPLOAD_PATH = "/var/ags/uploads/documents";
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String INVALID_FILE_SIZE = "File size exceeds the maximum allowed limit.";
    public static final String INVALID_FILE_TYPE = "Invalid file type. Only PDF and image files are allowed.";
} 