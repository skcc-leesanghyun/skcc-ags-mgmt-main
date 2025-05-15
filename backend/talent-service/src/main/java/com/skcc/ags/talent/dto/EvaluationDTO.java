package com.ags.talent.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class EvaluationDTO {
    private Long id;
    private Long candidateId;
    private String candidateName;

    @NotNull(message = "Score is required")
    @Min(value = 1, message = "Score must be between 1 and 5")
    @Max(value = 5, message = "Score must be between 1 and 5")
    private Integer score;

    private String comment;
    private String evaluator;
    private LocalDateTime evaluationDate;
} 