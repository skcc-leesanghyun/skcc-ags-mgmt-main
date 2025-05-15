package com.ags.talent.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Sow {
    private Long id;
    private String projectName;
    private String projectCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum Status {
        DRAFT,
        PENDING,
        APPROVED,
        REJECTED
    }
} 