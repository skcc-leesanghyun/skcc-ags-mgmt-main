package com.skcc.ags.talent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class for proposal feedback.
 */
@Entity
@Table(name = "proposal_feedback")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposalFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "proposal_id", nullable = false)
    private Long proposalId;

    @Column(name = "reviewer_id", nullable = false)
    private String reviewerId;

    @Column(name = "reviewer_name", nullable = false)
    private String reviewerName;

    @Column(name = "reviewer_department", nullable = false)
    private String reviewerDepartment;

    @Column(nullable = false, length = 2000)
    private String comments;

    @Column(name = "technical_score", nullable = false)
    private Integer technicalScore;

    @Column(name = "experience_score", nullable = false)
    private Integer experienceScore;

    @Column(name = "cost_score", nullable = false)
    private Integer costScore;

    @Column(name = "overall_score")
    private Double overallScore;

    @Column(length = 1000)
    private String strengths;

    @Column(name = "areas_for_improvement", length = 1000)
    private String areasForImprovement;

    @Column(length = 1000)
    private String requirements;

    @Column(length = 500)
    private String recommendation;

    @Column(name = "additional_notes", length = 1000)
    private String additionalNotes;

    @Column(name = "is_final_review")
    private boolean finalReview;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @PrePersist
    protected void onCreate() {
        if (overallScore == null && technicalScore != null && experienceScore != null && costScore != null) {
            calculateOverallScore();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (technicalScore != null && experienceScore != null && costScore != null) {
            calculateOverallScore();
        }
    }

    /**
     * Calculate the overall score based on weighted individual scores.
     */
    private void calculateOverallScore() {
        double technicalWeight = 0.4;
        double experienceWeight = 0.3;
        double costWeight = 0.3;

        this.overallScore = (technicalScore * technicalWeight) +
                           (experienceScore * experienceWeight) +
                           (costScore * costWeight);
    }

    /**
     * Check if this feedback has all required scores.
     *
     * @return true if all scores are present
     */
    public boolean hasAllScores() {
        return technicalScore != null && experienceScore != null && costScore != null;
    }

    /**
     * Check if this feedback is valid for final review.
     *
     * @return true if all required fields for final review are present
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