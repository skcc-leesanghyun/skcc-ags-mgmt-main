package com.skcc.ags.talent.dto;

import com.skcc.ags.talent.domain.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for transferring proposal information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposalDTO {
    
    /**
     * Unique identifier for the proposal
     */
    private Long id;

    /**
     * ID of the project this proposal is for
     */
    @NotNull(message = "Project ID is required")
    private Long projectId;

    /**
     * ID of the candidate being proposed
     */
    @NotNull(message = "Candidate ID is required")
    private Long candidateId;

    /**
     * ID of the partner company submitting the proposal
     */
    @NotNull(message = "Partner company ID is required")
    private Long partnerCompanyId;

    /**
     * Name of the partner company
     */
    @NotBlank(message = "Partner company name is required")
    private String partnerCompanyName;

    /**
     * Title of the proposal
     */
    @NotBlank(message = "Proposal title is required")
    private String title;

    /**
     * Detailed description of the proposal
     */
    private String description;

    /**
     * Current status of the proposal
     */
    @NotNull(message = "Proposal status is required")
    private ProposalStatus status;

    /**
     * Proposed start date for the candidate
     */
    @NotNull(message = "Proposed start date is required")
    private LocalDateTime proposedStartDate;

    /**
     * Expected duration in months
     */
    @NotNull(message = "Expected duration is required")
    private Integer expectedDurationMonths;

    /**
     * Proposed monthly rate in USD
     */
    @NotNull(message = "Proposed rate is required")
    private BigDecimal proposedRate;

    /**
     * List of relevant skills and expertise
     */
    private List<String> relevantSkills;

    /**
     * Years of relevant experience
     */
    private Integer yearsOfExperience;

    /**
     * Previous similar projects or experience
     */
    private String previousExperience;

    /**
     * Any special requirements or considerations
     */
    private String specialRequirements;

    /**
     * List of attached document references
     */
    private List<String> attachments;

    /**
     * List of feedback received for this proposal
     */
    private List<ProposalFeedbackDTO> feedbackList;

    /**
     * Timestamp when the proposal was submitted
     */
    private LocalDateTime submissionDate;

    /**
     * Timestamp for proposal expiration
     */
    private LocalDateTime expirationDate;

    /**
     * Timestamp when the proposal was created
     */
    private LocalDateTime createdAt;

    /**
     * ID of the user who created the proposal
     */
    private String createdBy;

    /**
     * Timestamp when the proposal was last modified
     */
    private LocalDateTime modifiedAt;

    /**
     * ID of the user who last modified the proposal
     */
    private String modifiedBy;

    /**
     * Flag indicating if the proposal has been reviewed
     */
    private boolean reviewed;

    /**
     * Flag indicating if the proposal is currently active
     */
    private boolean active;

    /**
     * Average score from all feedback
     */
    private Double averageScore;

    /**
     * Total number of feedback received
     */
    private Integer feedbackCount;
} 