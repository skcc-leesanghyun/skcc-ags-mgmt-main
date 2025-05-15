package com.skcc.ags.talent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * DTO for proposal feedback.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposalFeedbackDTO {

    private Long id;

    @NotNull(message = "Proposal ID is required")
    private Long proposalId;

    @NotBlank(message = "Reviewer ID is required")
    private String reviewerId;

    @NotBlank(message = "Reviewer name is required")
    private String reviewerName;

    @NotBlank(message = "Reviewer department is required")
    private String reviewerDepartment;

    @NotBlank(message = "Comments are required")
    @Size(min = 10, max = 2000, message = "Comments must be between 10 and 2000 characters")
    private String comments;

    @NotNull(message = "Technical score is required")
    @Min(value = 0, message = "Technical score must be at least 0")
    @Max(value = 100, message = "Technical score must not exceed 100")
    private Integer technicalScore;

    @NotNull(message = "Experience score is required")
    @Min(value = 0, message = "Experience score must be at least 0")
    @Max(value = 100, message = "Experience score must not exceed 100")
    private Integer experienceScore;

    @NotNull(message = "Cost score is required")
    @Min(value = 0, message = "Cost score must be at least 0")
    @Max(value = 100, message = "Cost score must not exceed 100")
    private Integer costScore;

    private Double overallScore;

    @Size(max = 1000, message = "Strengths must not exceed 1000 characters")
    private String strengths;

    @Size(max = 1000, message = "Areas for improvement must not exceed 1000 characters")
    private String areasForImprovement;

    @Size(max = 1000, message = "Requirements must not exceed 1000 characters")
    private String requirements;

    @Size(max = 500, message = "Recommendation must not exceed 500 characters")
    private String recommendation;

    @Size(max = 1000, message = "Additional notes must not exceed 1000 characters")
    private String additionalNotes;

    private boolean finalReview;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime modifiedAt;

    private String modifiedBy;

    /**
     * Check if this feedback has all required scores.
     *
     * @return true if all scores are present
     */
    public boolean hasAllScores() {
        return technicalScore != null && experienceScore != null && costScore != null;
    }

    /**
     * Check if this feedback has all required fields for final review.
     *
     * @return true if all required fields are present
     */
    public boolean isValidForFinalReview() {
        return hasAllScores() &&
               comments != null && !comments.trim().isEmpty() &&
               strengths != null && !strengths.trim().isEmpty() &&
               areasForImprovement != null && !areasForImprovement.trim().isEmpty() &&
               recommendation != null && !recommendation.trim().isEmpty();
    }

    /**
     * Check if this feedback needs attention based on scores.
     *
     * @param threshold the minimum acceptable score
     * @return true if any score is below the threshold
     */
    public boolean needsAttention(int threshold) {
        return hasAllScores() &&
               (technicalScore < threshold ||
                experienceScore < threshold ||
                costScore < threshold ||
                (overallScore != null && overallScore < threshold));
    }
} 