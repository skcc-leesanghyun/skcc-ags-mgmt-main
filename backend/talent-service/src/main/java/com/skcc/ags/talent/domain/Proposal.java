package com.skcc.ags.talent.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a proposal in the talent sourcing system
 */
@Entity
@Table(name = "proposals")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Proposal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "partner_company_id", nullable = false)
    private Long partnerCompanyId;

    @Column(name = "partner_company_name", nullable = false)
    private String partnerCompanyName;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status;

    @Column(name = "proposed_start_date", nullable = false)
    private LocalDateTime proposedStartDate;

    @Column(name = "expected_duration_months", nullable = false)
    private Integer expectedDurationMonths;

    @Column(name = "proposed_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal proposedRate;

    @ElementCollection
    @CollectionTable(
        name = "proposal_relevant_skills",
        joinColumns = @JoinColumn(name = "proposal_id")
    )
    @Column(name = "skill")
    private List<String> relevantSkills = new ArrayList<>();

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "previous_experience", columnDefinition = "TEXT")
    private String previousExperience;

    @Column(name = "special_requirements", columnDefinition = "TEXT")
    private String specialRequirements;

    @ElementCollection
    @CollectionTable(
        name = "proposal_attachments",
        joinColumns = @JoinColumn(name = "proposal_id")
    )
    @Column(name = "attachment_path")
    private List<String> attachments = new ArrayList<>();

    @OneToMany(
        mappedBy = "proposal",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ProposalFeedback> feedbackList = new ArrayList<>();

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    private boolean reviewed;

    private boolean active;

    @Column(name = "average_score")
    private Double averageScore;

    @Column(name = "feedback_count")
    private Integer feedbackCount;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        active = true;
        if (feedbackCount == null) {
            feedbackCount = 0;
        }
        if (status == null) {
            status = ProposalStatus.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
        updateAverageScore();
    }

    /**
     * Updates the average score based on feedback
     */
    private void updateAverageScore() {
        if (feedbackList != null && !feedbackList.isEmpty()) {
            double sum = feedbackList.stream()
                .mapToDouble(ProposalFeedback::getOverallScore)
                .average()
                .orElse(0.0);
            this.averageScore = sum;
            this.feedbackCount = feedbackList.size();
        }
    }

    /**
     * Adds feedback to the proposal
     * @param feedback The feedback to add
     */
    public void addFeedback(ProposalFeedback feedback) {
        feedbackList.add(feedback);
        feedback.setProposal(this);
        updateAverageScore();
    }

    /**
     * Removes feedback from the proposal
     * @param feedback The feedback to remove
     */
    public void removeFeedback(ProposalFeedback feedback) {
        feedbackList.remove(feedback);
        feedback.setProposal(null);
        updateAverageScore();
    }

    /**
     * Checks if the proposal can be edited
     * @return true if the proposal can be edited
     */
    public boolean canEdit() {
        return status != null && status.allowsEditing();
    }

    /**
     * Checks if the proposal can receive feedback
     * @return true if the proposal can receive feedback
     */
    public boolean canReceiveFeedback() {
        return status != null && status.allowsFeedback();
    }

    /**
     * Checks if the proposal requires review
     * @return true if the proposal requires review
     */
    public boolean requiresReview() {
        return status != null && status.requiresReview();
    }
} 