package com.skcc.ags.audit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skcc.ags.audit.domain.AuditStatus;
import com.skcc.ags.audit.domain.AuditType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transferring audit record data between layers.
 */
public class AuditRecordDTO {

    private Long id;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Personnel ID is required")
    private String personnelId;

    @NotBlank(message = "Personnel name is required")
    private String personnelName;

    @NotBlank(message = "Partner company is required")
    private String partnerCompany;

    @NotNull(message = "Audit type is required")
    private AuditType type;

    @NotNull(message = "Effective date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completionDate;

    @NotNull(message = "Status is required")
    private AuditStatus status;

    @Singular
    private List<SecurityDocumentDTO> documents = new ArrayList<>();

    private String notes;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public AuditRecordDTO() {}

    public AuditRecordDTO(Long id, Long projectId, String projectName, String personnelId, String personnelName, String partnerCompany, AuditType type, LocalDateTime effectiveDate, LocalDateTime completionDate, AuditStatus status, List<SecurityDocumentDTO> documents, String notes, String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.projectName = projectName;
        this.personnelId = personnelId;
        this.personnelName = personnelName;
        this.partnerCompany = partnerCompany;
        this.type = type;
        this.effectiveDate = effectiveDate;
        this.completionDate = completionDate;
        this.status = status;
        this.documents = documents;
        this.notes = notes;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getPersonnelId() { return personnelId; }

    public void setPersonnelId(String personnelId) { this.personnelId = personnelId; }

    public String getPersonnelName() { return personnelName; }

    public void setPersonnelName(String personnelName) { this.personnelName = personnelName; }

    public String getPartnerCompany() { return partnerCompany; }

    public void setPartnerCompany(String partnerCompany) { this.partnerCompany = partnerCompany; }

    public AuditType getType() { return type; }

    public void setType(AuditType type) { this.type = type; }

    public LocalDateTime getEffectiveDate() { return effectiveDate; }

    public void setEffectiveDate(LocalDateTime effectiveDate) { this.effectiveDate = effectiveDate; }

    public LocalDateTime getCompletionDate() { return completionDate; }

    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }

    public AuditStatus getStatus() { return status; }

    public void setStatus(AuditStatus status) { this.status = status; }

    public List<SecurityDocumentDTO> getDocuments() { return documents; }

    public void setDocuments(List<SecurityDocumentDTO> documents) { this.documents = documents; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

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
        private Long projectId;
        private String projectName;
        private String personnelId;
        private String personnelName;
        private String partnerCompany;
        private AuditType type;
        private LocalDateTime effectiveDate;
        private LocalDateTime completionDate;
        private AuditStatus status;
        private List<SecurityDocumentDTO> documents;
        private String notes;
        private String createdBy;
        private LocalDateTime createdAt;
        private String updatedBy;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }

        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }

        public Builder projectName(String projectName) { this.projectName = projectName; return this; }

        public Builder personnelId(String personnelId) { this.personnelId = personnelId; return this; }

        public Builder personnelName(String personnelName) { this.personnelName = personnelName; return this; }

        public Builder partnerCompany(String partnerCompany) { this.partnerCompany = partnerCompany; return this; }

        public Builder type(AuditType type) { this.type = type; return this; }

        public Builder effectiveDate(LocalDateTime effectiveDate) { this.effectiveDate = effectiveDate; return this; }

        public Builder completionDate(LocalDateTime completionDate) { this.completionDate = completionDate; return this; }

        public Builder status(AuditStatus status) { this.status = status; return this; }

        public Builder documents(List<SecurityDocumentDTO> documents) { this.documents = documents; return this; }

        public Builder notes(String notes) { this.notes = notes; return this; }

        public Builder createdBy(String createdBy) { this.createdBy = createdBy; return this; }

        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Builder updatedBy(String updatedBy) { this.updatedBy = updatedBy; return this; }

        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public AuditRecordDTO build() { return new AuditRecordDTO(id, projectId, projectName, personnelId, personnelName, partnerCompany, type, effectiveDate, completionDate, status, documents, notes, createdBy, createdAt, updatedBy, updatedAt); }
    }

    /**
     * Checks if this audit record has all required security documents
     * @return true if all required documents are present, false otherwise
     */
    public boolean hasAllRequiredDocuments() {
        return documents != null && documents.stream()
                .anyMatch(doc -> doc.getType() != null && "SECURITY_PLEDGE".equals(doc.getType().name()));
    }
} 