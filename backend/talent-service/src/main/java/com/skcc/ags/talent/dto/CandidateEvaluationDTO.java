package com.ags.talent.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CandidateEvaluationDTO {
    private Long id;

    @NotNull(message = "Evaluator ID is required")
    private Long evaluatorId;

    @NotBlank(message = "Evaluator name is required")
    private String evaluatorName;

    @NotNull(message = "Technical score is required")
    @Min(value = 1, message = "Technical score must be between 1 and 5")
    @Max(value = 5, message = "Technical score must be between 1 and 5")
    private Integer technicalScore;

    @NotNull(message = "Communication score is required")
    @Min(value = 1, message = "Communication score must be between 1 and 5")
    @Max(value = 5, message = "Communication score must be between 1 and 5")
    private Integer communicationScore;

    @NotNull(message = "Problem solving score is required")
    @Min(value = 1, message = "Problem solving score must be between 1 and 5")
    @Max(value = 5, message = "Problem solving score must be between 1 and 5")
    private Integer problemSolvingScore;

    @NotNull(message = "Team fit score is required")
    @Min(value = 1, message = "Team fit score must be between 1 and 5")
    @Max(value = 5, message = "Team fit score must be between 1 and 5")
    private Integer teamFitScore;

    private String strengths;
    private String weaknesses;
    private String comments;
    private String evaluationDate;
    private String status; // e.g., "PENDING", "COMPLETED"
} 