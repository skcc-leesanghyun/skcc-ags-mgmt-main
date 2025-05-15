package com.skcc.ags.audit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skcc.ags.audit.domain.DocumentStatus;
import com.skcc.ags.audit.domain.SecurityDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for transferring security document data between layers.
 */
public class SecurityDocumentDTO {

    private Long id;

    private Long auditRecordId;

    @NotNull(message = "Document type is required")
    private SecurityDocumentType type;

    @NotBlank(message = "File name is required")
    private String fileName;

    @NotBlank(message = "File path is required")
    private String filePath;

    private Long fileSize;

    private String mimeType;

    @NotNull(message = "Submission date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submissionDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiryDate;

    @NotNull(message = "Status is required")
    private DocumentStatus status;

    private String comments;

    private String reviewedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviewDate;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Boolean required;

    private Boolean confidential;

    private AuditRecordDTO auditRecord;
    private SecurityDocumentType documentType;

    public SecurityDocumentDTO() {}

    public SecurityDocumentDTO(Long id, Long auditRecordId, SecurityDocumentType type, String fileName, String filePath, Long fileSize, String mimeType, LocalDateTime submissionDate, LocalDateTime expiryDate, DocumentStatus status, String comments, String reviewedBy, LocalDateTime reviewDate, String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
        this.id = id;
        this.auditRecordId = auditRecordId;
        this.type = type;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.submissionDate = submissionDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.comments = comments;
        this.reviewedBy = reviewedBy;
        this.reviewDate = reviewDate;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAuditRecordId() { return auditRecordId; }
    public void setAuditRecordId(Long auditRecordId) { this.auditRecordId = auditRecordId; }
    public SecurityDocumentType getType() { return type; }
    public void setType(SecurityDocumentType type) { this.type = type; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
    public DocumentStatus getStatus() { return status; }
    public void setStatus(DocumentStatus status) { this.status = status; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public String getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; }
    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long auditRecordId;
        private SecurityDocumentType type;
        private String fileName;
        private String filePath;
        private Long fileSize;
        private String mimeType;
        private LocalDateTime submissionDate;
        private LocalDateTime expiryDate;
        private DocumentStatus status;
        private String comments;
        private String reviewedBy;
        private LocalDateTime reviewDate;
        private String createdBy;
        private LocalDateTime createdAt;
        private String updatedBy;
        private LocalDateTime updatedAt;
        private AuditRecordDTO auditRecord;
        private SecurityDocumentType documentType;
        private Boolean required;
        private Boolean confidential;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder auditRecordId(Long auditRecordId) { this.auditRecordId = auditRecordId; return this; }
        public Builder type(SecurityDocumentType type) { this.type = type; return this; }
        public Builder fileName(String fileName) { this.fileName = fileName; return this; }
        public Builder filePath(String filePath) { this.filePath = filePath; return this; }
        public Builder fileSize(Long fileSize) { this.fileSize = fileSize; return this; }
        public Builder mimeType(String mimeType) { this.mimeType = mimeType; return this; }
        public Builder submissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; return this; }
        public Builder expiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; return this; }
        public Builder status(DocumentStatus status) { this.status = status; return this; }
        public Builder comments(String comments) { this.comments = comments; return this; }
        public Builder reviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; return this; }
        public Builder reviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; return this; }
        public Builder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedBy(String updatedBy) { this.updatedBy = updatedBy; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder auditRecord(AuditRecordDTO auditRecord) { this.auditRecord = auditRecord; return this; }
        public Builder documentType(SecurityDocumentType documentType) { this.documentType = documentType; this.type = documentType; return this; }
        public Builder required(Boolean required) { this.required = required; return this; }
        public Builder confidential(Boolean confidential) { this.confidential = confidential; return this; }
        public SecurityDocumentDTO build() { return new SecurityDocumentDTO(id, auditRecordId, type, fileName, filePath, fileSize, mimeType, submissionDate, expiryDate, status, comments, reviewedBy, reviewDate, createdBy, createdAt, updatedBy, updatedAt); }
    }

    public SecurityDocumentType getDocumentType() { return documentType != null ? documentType : type; }
    public void setDocumentType(SecurityDocumentType documentType) { this.documentType = documentType; this.type = documentType; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }
    public Boolean getConfidential() { return confidential; }
    public void setConfidential(Boolean confidential) { this.confidential = confidential; }

    /**
     * Checks if the document has expired
     * @return true if the document has expired, false otherwise
     */
    public boolean isExpired() {
        return expiryDate != null && LocalDateTime.now().isAfter(expiryDate);
    }

    public void updateExpiryStatus() {}

    public void updateDisplayLabels() {}

    public AuditRecordDTO getAuditRecord() { return auditRecord; }
    public void setAuditRecord(AuditRecordDTO auditRecord) { this.auditRecord = auditRecord; }
} 