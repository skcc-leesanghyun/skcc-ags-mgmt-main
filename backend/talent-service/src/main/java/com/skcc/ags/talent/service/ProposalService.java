package com.skcc.ags.talent.service;

import com.skcc.ags.talent.domain.ProposalStatus;
import com.skcc.ags.talent.dto.ProposalDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for managing proposals
 */
public interface ProposalService {

    /**
     * Create a new proposal
     * @param proposalDTO The proposal data
     * @return Created proposal
     */
    ProposalDTO createProposal(ProposalDTO proposalDTO);

    /**
     * Update an existing proposal
     * @param id The proposal ID
     * @param proposalDTO The updated proposal data
     * @return Updated proposal
     */
    ProposalDTO updateProposal(Long id, ProposalDTO proposalDTO);

    /**
     * Get proposal by ID
     * @param id The proposal ID
     * @return Optional containing the proposal if found
     */
    Optional<ProposalDTO> getProposalById(Long id);

    /**
     * Get proposals by project ID
     * @param projectId The project ID
     * @param pageable Pagination information
     * @return Page of proposals for the project
     */
    Page<ProposalDTO> getProposalsByProject(Long projectId, Pageable pageable);

    /**
     * Get proposals by candidate ID
     * @param candidateId The candidate ID
     * @param pageable Pagination information
     * @return Page of proposals for the candidate
     */
    Page<ProposalDTO> getProposalsByCandidate(Long candidateId, Pageable pageable);

    /**
     * Get proposals by partner company ID
     * @param partnerCompanyId The partner company ID
     * @param pageable Pagination information
     * @return Page of proposals from the partner company
     */
    Page<ProposalDTO> getProposalsByPartnerCompany(Long partnerCompanyId, Pageable pageable);

    /**
     * Submit a proposal for review
     * @param id The proposal ID
     * @return Updated proposal
     */
    ProposalDTO submitProposal(Long id);

    /**
     * Add feedback to a proposal
     * @param proposalId The proposal ID
     * @param feedbackDTO The feedback data
     * @return Added feedback
     */
    ProposalFeedbackDTO addFeedback(Long proposalId, ProposalFeedbackDTO feedbackDTO);

    /**
     * Update proposal status
     * @param id The proposal ID
     * @param status The new status
     * @param comment Optional comment explaining the status change
     * @return Updated proposal
     */
    ProposalDTO updateStatus(Long id, ProposalStatus status, String comment);

    /**
     * Get proposals requiring review
     * @param pageable Pagination information
     * @return Page of proposals that need review
     */
    Page<ProposalDTO> getProposalsRequiringReview(Pageable pageable);

    /**
     * Get proposals by status
     * @param status The proposal status
     * @param pageable Pagination information
     * @return Page of proposals with the given status
     */
    Page<ProposalDTO> getProposalsByStatus(ProposalStatus status, Pageable pageable);

    /**
     * Search proposals by keyword
     * @param keyword The search keyword
     * @param pageable Pagination information
     * @return Page of proposals matching the search criteria
     */
    Page<ProposalDTO> searchProposals(String keyword, Pageable pageable);

    /**
     * Get proposals expiring soon
     * @param threshold The expiration date threshold
     * @param pageable Pagination information
     * @return Page of proposals expiring before the threshold
     */
    Page<ProposalDTO> getExpiringProposals(LocalDateTime threshold, Pageable pageable);

    /**
     * Get high scoring proposals
     * @param minimumScore The minimum score threshold
     * @param pageable Pagination information
     * @return Page of proposals with scores above the threshold
     */
    Page<ProposalDTO> getHighScoringProposals(Double minimumScore, Pageable pageable);

    /**
     * Get proposal statistics
     * @return Map containing various proposal statistics
     */
    ProposalStatistics getProposalStatistics();

    /**
     * Delete a proposal
     * @param id The proposal ID
     */
    void deleteProposal(Long id);

    /**
     * Get all feedback for a proposal
     * @param proposalId The proposal ID
     * @return List of feedback for the proposal
     */
    List<ProposalFeedbackDTO> getProposalFeedback(Long proposalId);

    /**
     * Get final review feedback for a proposal
     * @param proposalId The proposal ID
     * @return Optional containing the final review feedback if found
     */
    Optional<ProposalFeedbackDTO> getFinalReviewFeedback(Long proposalId);

    /**
     * Calculate average scores for a proposal
     * @param proposalId The proposal ID
     * @return Updated proposal with recalculated scores
     */
    ProposalDTO recalculateScores(Long proposalId);

    /**
     * Extend proposal expiration date
     * @param id The proposal ID
     * @param newExpirationDate The new expiration date
     * @return Updated proposal
     */
    ProposalDTO extendExpirationDate(Long id, LocalDateTime newExpirationDate);

    /**
     * Get proposals by multiple statuses
     * @param statuses List of proposal statuses
     * @param pageable Pagination information
     * @return Page of proposals matching any of the given statuses
     */
    Page<ProposalDTO> getProposalsByStatuses(List<ProposalStatus> statuses, Pageable pageable);
}

/**
 * Statistics for proposals
 */
class ProposalStatistics {
    private long totalProposals;
    private long activeProposals;
    private long pendingReview;
    private long approvedProposals;
    private long rejectedProposals;
    private double averageScore;
    private long highScoringProposals;
    private long expiringProposals;
    private Map<ProposalStatus, Long> proposalsByStatus;
    private Map<String, Double> averageScoresByDepartment;
    
    // Getters and setters
} 