package com.ags.talent.dto;

import lombok.Data;

@Data
public class CandidateFileDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long fileSize;
    private String uploadedBy;
    private String uploadedAt;
} 