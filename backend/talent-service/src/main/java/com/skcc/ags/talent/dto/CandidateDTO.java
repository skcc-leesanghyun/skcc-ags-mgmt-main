package com.ags.talent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CandidateDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotBlank(message = "Current location is required")
    private String currentLocation;

    @NotBlank(message = "Primary skill is required")
    private String primarySkill;

    private List<String> secondarySkills;

    @NotNull(message = "Years of experience is required")
    private Integer yearsOfExperience;

    private String educationBackground;
    private String certifications;
    private String languageSkills;
    private String visaStatus;
    private String availability;
    private String expectedSalary;
    private String notes;

    private List<CandidateFileDTO> files;
    private List<CandidateEvaluationDTO> evaluations;
} 