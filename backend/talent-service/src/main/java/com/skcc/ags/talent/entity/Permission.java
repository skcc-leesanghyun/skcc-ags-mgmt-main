package com.ags.talent.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    private Long id;
    private String permissionName;
    private String description;
    private String category;
    private Boolean isSystem;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static class Status {
        public static final String ACTIVE = "ACTIVE";
        public static final String INACTIVE = "INACTIVE";
        public static final String DELETED = "DELETED";
    }

    public static class Categories {
        public static final String USER_MANAGEMENT = "USER_MANAGEMENT";
        public static final String PROJECT_MANAGEMENT = "PROJECT_MANAGEMENT";
        public static final String CANDIDATE_MANAGEMENT = "CANDIDATE_MANAGEMENT";
        public static final String EVALUATION_MANAGEMENT = "EVALUATION_MANAGEMENT";
        public static final String DOCUMENT_MANAGEMENT = "DOCUMENT_MANAGEMENT";
        public static final String SYSTEM_MANAGEMENT = "SYSTEM_MANAGEMENT";
        public static final String AUDIT_MANAGEMENT = "AUDIT_MANAGEMENT";
    }

    public static class Names {
        // User Management Permissions
        public static final String VIEW_USERS = "VIEW_USERS";
        public static final String CREATE_USER = "CREATE_USER";
        public static final String UPDATE_USER = "UPDATE_USER";
        public static final String DELETE_USER = "DELETE_USER";
        
        // Project Management Permissions
        public static final String VIEW_PROJECTS = "VIEW_PROJECTS";
        public static final String CREATE_PROJECT = "CREATE_PROJECT";
        public static final String UPDATE_PROJECT = "UPDATE_PROJECT";
        public static final String DELETE_PROJECT = "DELETE_PROJECT";
        
        // Candidate Management Permissions
        public static final String VIEW_CANDIDATES = "VIEW_CANDIDATES";
        public static final String CREATE_CANDIDATE = "CREATE_CANDIDATE";
        public static final String UPDATE_CANDIDATE = "UPDATE_CANDIDATE";
        public static final String DELETE_CANDIDATE = "DELETE_CANDIDATE";
        
        // Evaluation Management Permissions
        public static final String VIEW_EVALUATIONS = "VIEW_EVALUATIONS";
        public static final String CREATE_EVALUATION = "CREATE_EVALUATION";
        public static final String UPDATE_EVALUATION = "UPDATE_EVALUATION";
        public static final String DELETE_EVALUATION = "DELETE_EVALUATION";
        
        // Document Management Permissions
        public static final String VIEW_DOCUMENTS = "VIEW_DOCUMENTS";
        public static final String UPLOAD_DOCUMENT = "UPLOAD_DOCUMENT";
        public static final String UPDATE_DOCUMENT = "UPDATE_DOCUMENT";
        public static final String DELETE_DOCUMENT = "DELETE_DOCUMENT";
        
        // System Management Permissions
        public static final String VIEW_SYSTEM_SETTINGS = "VIEW_SYSTEM_SETTINGS";
        public static final String UPDATE_SYSTEM_SETTINGS = "UPDATE_SYSTEM_SETTINGS";
        public static final String VIEW_LOGS = "VIEW_LOGS";
        
        // Audit Management Permissions
        public static final String VIEW_AUDIT_LOGS = "VIEW_AUDIT_LOGS";
        public static final String EXPORT_AUDIT_LOGS = "EXPORT_AUDIT_LOGS";
    }
} 