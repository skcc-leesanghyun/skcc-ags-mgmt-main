package com.skcc.ags.talent.repository;

import com.skcc.ags.talent.entity.ProposalFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for ProposalFeedback entity.
 */
@Repository
public interface ProposalFeedbackRepository extends JpaRepository<ProposalFeedback, Long> {

    /**
     * Find all feedback for a specific proposal.
     *
     * @param proposalId the ID of the proposal
     * @param pageable pagination information
     * @return a page of feedback
     */
    Page<ProposalFeedback> findByProposalId(Long proposalId, Pageable pageable);

    /**
     * Find all feedback by a specific reviewer.
     *
     * @param reviewerId the ID of the reviewer
     * @param pageable pagination information
     * @return a page of feedback
     */
    Page<ProposalFeedback> findByReviewerId(String reviewerId, Pageable pageable);

    /**
     * Find all feedback from a specific department.
     *
     * @param reviewerDepartment the department name
     * @param pageable pagination information
     * @return a page of feedback
     */
    Page<ProposalFeedback> findByReviewerDepartment(String reviewerDepartment, Pageable pageable);

    /**
     * Find feedback with overall score greater than or equal to the minimum score.
     *
     * @param minimumScore the minimum score threshold
     * @param pageable pagination information
     * @return a page of high-scoring feedback
     */
    @Query("SELECT f FROM ProposalFeedback f WHERE f.overallScore >= :minimumScore")
    Page<ProposalFeedback> findHighScoringFeedback(@Param("minimumScore") Double minimumScore, Pageable pageable);

    /**
     * Find feedback requiring attention (scores below threshold).
     *
     * @param threshold the score threshold
     * @param pageable pagination information
     * @return a page of feedback requiring attention
     */
    @Query("SELECT f FROM ProposalFeedback f WHERE " +
           "f.technicalScore < :threshold OR " +
           "f.experienceScore < :threshold OR " +
           "f.costScore < :threshold OR " +
           "f.overallScore < :threshold")
    Page<ProposalFeedback> findFeedbackRequiringAttention(@Param("threshold") Integer threshold, Pageable pageable);

    /**
     * Find feedback created within a date range.
     *
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @param pageable pagination information
     * @return a page of feedback within the date range
     */
    Page<ProposalFeedback> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Find the latest feedback for a proposal.
     *
     * @param proposalId the ID of the proposal
     * @return the latest feedback, if any
     */
    Optional<ProposalFeedback> findFirstByProposalIdOrderByCreatedAtDesc(Long proposalId);

    /**
     * Calculate average scores by department.
     *
     * @param department the department name
     * @return average technical, experience, cost, and overall scores
     */
    @Query("SELECT new map(" +
           "AVG(f.technicalScore) as technicalAvg, " +
           "AVG(f.experienceScore) as experienceAvg, " +
           "AVG(f.costScore) as costAvg, " +
           "AVG(f.overallScore) as overallAvg) " +
           "FROM ProposalFeedback f " +
           "WHERE f.reviewerDepartment = :department")
    Map<String, Double> calculateAverageScoresByDepartment(@Param("department") String department);

    /**
     * Find all final review feedback for a department.
     *
     * @param department the department name
     * @return list of final review feedback
     */
    List<ProposalFeedback> findByReviewerDepartmentAndFinalReviewIsTrue(String department);

    /**
     * Count feedback by department and creation date range.
     *
     * @param department the department name
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return count of feedback
     */
    long countByReviewerDepartmentAndCreatedAtBetween(
            String department,
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Find feedback with incomplete required fields.
     *
     * @param pageable pagination information
     * @return a page of incomplete feedback
     */
    @Query("SELECT f FROM ProposalFeedback f WHERE " +
           "f.comments IS NULL OR " +
           "f.technicalScore IS NULL OR " +
           "f.experienceScore IS NULL OR " +
           "f.costScore IS NULL")
    Page<ProposalFeedback> findIncompleteFeeback(Pageable pageable);

    /**
     * Check if a reviewer has already provided feedback for a proposal.
     *
     * @param proposalId the ID of the proposal
     * @param reviewerId the ID of the reviewer
     * @return true if feedback exists
     */
    boolean existsByProposalIdAndReviewerId(Long proposalId, String reviewerId);
} 