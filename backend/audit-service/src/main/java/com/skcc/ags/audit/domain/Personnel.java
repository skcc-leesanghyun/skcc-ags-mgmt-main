package com.skcc.ags.audit.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Personnel {
    private Long id;
    private Long projectId;
    private String employeeId;
    private String name;
    private String role;
    private String department;
    private String company;
    private String startDate;
    private String endDate;
    private String pledgeStatus;
    private LocalDate pledgeSubmissionDate;
    private String pledgeFilePath;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public Personnel() {}
    public Personnel(Long id, Long projectId, String employeeId, String name, String role, String department, String company, String startDate, String endDate, String pledgeStatus, LocalDate pledgeSubmissionDate, String pledgeFilePath, String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.department = department;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pledgeStatus = pledgeStatus;
        this.pledgeSubmissionDate = pledgeSubmissionDate;
        this.pledgeFilePath = pledgeFilePath;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public String getPledgeStatus() { return pledgeStatus; }
    public void setPledgeStatus(String pledgeStatus) { this.pledgeStatus = pledgeStatus; }
    public LocalDate getPledgeSubmissionDate() { return pledgeSubmissionDate; }
    public void setPledgeSubmissionDate(LocalDate pledgeSubmissionDate) { this.pledgeSubmissionDate = pledgeSubmissionDate; }
    public String getPledgeFilePath() { return pledgeFilePath; }
    public void setPledgeFilePath(String pledgeFilePath) { this.pledgeFilePath = pledgeFilePath; }
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
        private String employeeId;
        private String name;
        private String role;
        private String department;
        private String company;
        private String startDate;
        private String endDate;
        private String pledgeStatus;
        private LocalDate pledgeSubmissionDate;
        private String pledgeFilePath;
        private String createdBy;
        private LocalDateTime createdAt;
        private String updatedBy;
        private LocalDateTime updatedAt;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }
        public Builder employeeId(String employeeId) { this.employeeId = employeeId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder role(String role) { this.role = role; return this; }
        public Builder department(String department) { this.department = department; return this; }
        public Builder company(String company) { this.company = company; return this; }
        public Builder startDate(String startDate) { this.startDate = startDate; return this; }
        public Builder endDate(String endDate) { this.endDate = endDate; return this; }
        public Builder pledgeStatus(String pledgeStatus) { this.pledgeStatus = pledgeStatus; return this; }
        public Builder pledgeSubmissionDate(LocalDate pledgeSubmissionDate) { this.pledgeSubmissionDate = pledgeSubmissionDate; return this; }
        public Builder pledgeFilePath(String pledgeFilePath) { this.pledgeFilePath = pledgeFilePath; return this; }
        public Builder createdBy(String createdBy) { this.createdBy = createdBy; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedBy(String updatedBy) { this.updatedBy = updatedBy; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Personnel build() { return new Personnel(id, projectId, employeeId, name, role, department, company, startDate, endDate, pledgeStatus, pledgeSubmissionDate, pledgeFilePath, createdBy, createdAt, updatedBy, updatedAt); }
    }
} 