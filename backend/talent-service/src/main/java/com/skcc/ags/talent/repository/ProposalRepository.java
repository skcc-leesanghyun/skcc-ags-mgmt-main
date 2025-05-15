package com.skcc.ags.talent.repository;

import com.skcc.ags.talent.domain.Proposal;
import com.skcc.ags.talent.domain.ProposalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Proposal entity
 */
@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    /**
     * Find proposals by project ID
     * @param projectId The project ID
     * @return List of proposals for the project
     */
    List<Proposal> findByProjectId(Long projectId);

    /**
     * Find proposals by candidate ID
     * @param candidateId The candidate ID
     * @return List of proposals for the candidate
     */
    List<Proposal> findByCandidateId(Long candidateId);

    /**
     * Find proposals by partner company ID
     * @param partnerCompanyId The partner company ID
     * @return List of proposals for the partner company
     */
    List<Proposal> findByPartnerCompanyId(Long partnerCompanyId);

    /**
     * Find proposals by status
     * @param status The proposal status
     * @param pageable Pagination information
     * @return Page of proposals with the given status
     */
    Page<Proposal> findByStatus(ProposalStatus status, Pageable pageable);

    /**
     * Find active proposals by project ID
     * @param projectId The project ID
     * @param pageable Pagination information
     * @return Page of active proposals for the project
     */
    Page<Proposal> findByProjectIdAndActiveTrue(Long projectId, Pageable pageable);

    /**
     * Find proposals requiring review
     * @param pageable Pagination information
     * @return Page of proposals that need review
     */
    @Query("SELECT p FROM Proposal p WHERE p.status = 'SUBMITTED' OR p.status = 'IN_REVIEW'")
    Page<Proposal> findProposalsRequiringReview(Pageable pageable);

    /**
     * Find proposals by status and submission date range
     * @param status The proposal status
     * @param startDate Start of the date range
     * @param endDate End of the date range
     * @param pageable Pagination information
     * @return Page of proposals matching the criteria
     */
    Page<Proposal> findByStatusAndSubmissionDateBetween(
        ProposalStatus status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Pageable pageable
    );

    /**
     * Find proposals expiring soon
     * @param expirationDate The expiration date threshold
     * @param pageable Pagination information
     * @return Page of proposals expiring before the given date
     */
    Page<Proposal> findByExpirationDateBeforeAndStatusNot(
        LocalDateTime expirationDate,
        ProposalStatus status,
        Pageable pageable
    );

    /**
     * Find proposals by project ID and status
     * @param projectId The project ID
     * @param status The proposal status
     * @return List of proposals matching the criteria
     */
    List<Proposal> findByProjectIdAndStatus(Long projectId, ProposalStatus status);

    /**
     * Find proposals with high scores
     * @param minimumScore The minimum score threshold
     * @param pageable Pagination information
     * @return Page of proposals with scores above the threshold
     */
    @Query("SELECT p FROM Proposal p WHERE p.averageScore >= :minimumScore")
    Page<Proposal> findHighScoringProposals(@Param("minimumScore") Double minimumScore, Pageable pageable);

    /**
     * Count proposals by status
     * @param status The proposal status
     * @return Number of proposals with the given status
     */
    long countByStatus(ProposalStatus status);

    /**
     * Find proposal by ID with feedback eagerly loaded
     * @param id The proposal ID
     * @return Optional containing the proposal with feedback if found
     */
    @Query("SELECT p FROM Proposal p LEFT JOIN FETCH p.feedbackList WHERE p.id = :id")
    Optional<Proposal> findByIdWithFeedback(@Param("id") Long id);

    /**
     * Find proposals by multiple statuses
     * @param statuses List of proposal statuses
     * @param pageable Pagination information
     * @return Page of proposals matching any of the given statuses
     */
    Page<Proposal> findByStatusIn(List<ProposalStatus> statuses, Pageable pageable);

    /**
     * Search proposals by keyword in title or description
     * @param keyword The search keyword
     * @param pageable Pagination information
     * @return Page of proposals matching the search criteria
     */
    @Query("SELECT p FROM Proposal p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Proposal> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * Find proposals pending review for more than specified days
     * @param threshold The date threshold
     * @param pageable Pagination information
     * @return Page of proposals pending review for longer than the threshold
     */
    @Query("SELECT p FROM Proposal p WHERE p.status IN ('SUBMITTED', 'IN_REVIEW') " +
           "AND p.submissionDate < :threshold")
    Page<Proposal> findLongPendingProposals(@Param("threshold") LocalDateTime threshold, Pageable pageable);
} 