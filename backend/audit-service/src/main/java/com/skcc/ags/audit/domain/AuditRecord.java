package com.skcc.ags.audit.domain;

import java.time.LocalDateTime;
import java.util.List;

public class AuditRecord {
    private Long id;
    private Long projectId;
    private String personnelId;
    private String partnerCompany;
    private AuditType type;
    private AuditStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private List<SecurityDocument> documents;

    public AuditRecord() {}
    public AuditRecord(Long id, Long projectId, String personnelId, String partnerCompany, AuditType type, AuditStatus status, String notes, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, List<SecurityDocument> documents) {
        this.id = id;
        this.projectId = projectId;
        this.personnelId = personnelId;
        this.partnerCompany = partnerCompany;
        this.type = type;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.documents = documents;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public String getPersonnelId() { return personnelId; }
    public void setPersonnelId(String personnelId) { this.personnelId = personnelId; }
    public String getPartnerCompany() { return partnerCompany; }
    public void setPartnerCompany(String partnerCompany) { this.partnerCompany = partnerCompany; }
    public AuditType getType() { return type; }
    public void setType(AuditType type) { this.type = type; }
    public AuditStatus getStatus() { return status; }
    public void setStatus(AuditStatus status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public List<SecurityDocument> getDocuments() { return documents; }
    public void setDocuments(List<SecurityDocument> documents) { this.documents = documents; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id;
        private Long projectId;
        private String personnelId;
        private String partnerCompany;
        private AuditType type;
        private AuditStatus status;
        private String notes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
        private List<SecurityDocument> documents;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }
        public Builder personnelId(String personnelId) { this.personnelId = personnelId; return this; }
        public Builder partnerCompany(String partnerCompany) { this.partnerCompany = partnerCompany; return this; }
        public Builder type(AuditType type) { this.type = type; return this; }
        public Builder status(AuditStatus status) { this.status = status; return this; }
        public Builder notes(String notes) { this.notes = notes; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public Builder updatedBy(String updatedBy) { this.updatedBy = updatedBy; return this; }
        public Builder documents(List<SecurityDocument> documents) { this.documents = documents; return this; }
        public AuditRecord build() { return new AuditRecord(id, projectId, personnelId, partnerCompany, type, status, notes, createdAt, updatedAt, createdBy, updatedBy, documents); }
    }
} 