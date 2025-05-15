package com.skcc.ags.talent.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * Entity representing feedback for a proposal in the talent sourcing system
 */
@Entity
@Table(name = "proposal_feedback")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class ProposalFeedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposal_id", nullable = false)
    private Proposal proposal;

    @Column(name = "reviewer_id", nullable = false)
    private String reviewerId;

    @Column(name = "reviewer_name", nullable = false)
    private String reviewerName;

    @Column(name = "reviewer_department", nullable = false)
    private String reviewerDepartment;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Min(1)
    @Max(5)
    @Column(name = "technical_score")
    private Integer technicalScore;

    @Min(1)
    @Max(5)
    @Column(name = "experience_score")
    private Integer experienceScore;

    @Min(1)
    @Max(5)
    @Column(name = "cost_score")
    private Integer costScore;

    @Min(1)
    @Max(5)
    @Column(name = "overall_score")
    private Integer overallScore;

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(name = "areas_for_improvement", columnDefinition = "TEXT")
    private String areasForImprovement;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(nullable = false)
    private String recommendation;

    @Column(name = "additional_notes", columnDefinition = "TEXT")
    private String additionalNotes;

    @Column(name = "final_review")
    private boolean finalReview;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        calculateOverallScore();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
        calculateOverallScore();
    }

    /**
     * Calculates the overall score based on individual scores
     */
    private void calculateOverallScore() {
        if (technicalScore != null && experienceScore != null && costScore != null) {
            // Weight distribution: Technical (40%), Experience (35%), Cost (25%)
            double weightedScore = (technicalScore * 0.4) +
                                 (experienceScore * 0.35) +
                                 (costScore * 0.25);
            this.overallScore = (int) Math.round(weightedScore);
        }
    }

    /**
     * Checks if the feedback is positive
     * @return true if the overall score is 4 or higher
     */
    public boolean isPositive() {
        return overallScore != null && overallScore >= 4;
    }

    /**
     * Checks if all required scores are provided
     * @return true if all score fields are filled
     */
    public boolean hasCompleteScores() {
        return technicalScore != null &&
               experienceScore != null &&
               costScore != null;
    }

    /**
     * Gets the feedback status based on the recommendation
     * @return the status derived from the recommendation
     */
    public String getFeedbackStatus() {
        if (recommendation == null) {
            return "PENDING";
        }
        return switch (recommendation.toUpperCase()) {
            case "APPROVE" -> "APPROVED";
            case "REJECT" -> "REJECTED";
            case "NEEDS_REVISION" -> "REVISION_REQUIRED";
            default -> "UNDER_REVIEW";
        };
    }
} 