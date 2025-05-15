package com.skcc.ags.talent.service;

import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackStatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing proposal feedback.
 */
public interface ProposalFeedbackService {

    /**
     * Create new feedback.
     *
     * @param feedbackDTO the feedback data to create
     * @return the created feedback
     */
    ProposalFeedbackDTO createFeedback(ProposalFeedbackDTO feedbackDTO);

    /**
     * Update existing feedback.
     *
     * @param id the ID of the feedback to update
     * @param feedbackDTO the updated feedback data
     * @return the updated feedback
     */
    ProposalFeedbackDTO updateFeedback(Long id, ProposalFeedbackDTO feedbackDTO);

    /**
     * Delete feedback by ID.
     *
     * @param id the ID of the feedback to delete
     */
    void deleteFeedback(Long id);

    /**
     * Get feedback by ID.
     *
     * @param id the ID of the feedback
     * @return the feedback data
     */
    ProposalFeedbackDTO getFeedbackById(Long id);

    /**
     * Get all feedback for a proposal.
     *
     * @param proposalId the ID of the proposal
     * @param pageable pagination information
     * @return page of feedback
     */
    Page<ProposalFeedbackDTO> getFeedbackByProposal(Long proposalId, Pageable pageable);

    /**
     * Get all feedback by a reviewer.
     *
     * @param reviewerId the ID of the reviewer
     * @param pageable pagination information
     * @return page of feedback
     */
    Page<ProposalFeedbackDTO> getFeedbackByReviewer(String reviewerId, Pageable pageable);

    /**
     * Get all feedback from a department.
     *
     * @param department the department name
     * @param pageable pagination information
     * @return page of feedback
     */
    Page<ProposalFeedbackDTO> getFeedbackByDepartment(String department, Pageable pageable);

    /**
     * Get high-scoring feedback.
     *
     * @param minimumScore the minimum score threshold
     * @param pageable pagination information
     * @return page of high-scoring feedback
     */
    Page<ProposalFeedbackDTO> getHighScoringFeedback(Double minimumScore, Pageable pageable);

    /**
     * Get feedback requiring attention.
     *
     * @param threshold the score threshold
     * @param pageable pagination information
     * @return page of feedback requiring attention
     */
    Page<ProposalFeedbackDTO> getFeedbackRequiringAttention(Integer threshold, Pageable pageable);

    /**
     * Get feedback statistics for a department.
     *
     * @param department the department name
     * @return the department's feedback statistics
     */
    ProposalFeedbackStatisticsDTO getDepartmentStatistics(String department);

    /**
     * Get feedback within a date range.
     *
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @param pageable pagination information
     * @return page of feedback
     */
    Page<ProposalFeedbackDTO> getFeedbackByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Get the latest feedback for a proposal.
     *
     * @param proposalId the ID of the proposal
     * @return the latest feedback, if any
     */
    ProposalFeedbackDTO getLatestFeedback(Long proposalId);

    /**
     * Get all final review feedback for a department.
     *
     * @param department the department name
     * @return list of final review feedback
     */
    List<ProposalFeedbackDTO> getFinalReviewsByDepartment(String department);

    /**
     * Get incomplete feedback.
     *
     * @param pageable pagination information
     * @return page of incomplete feedback
     */
    Page<ProposalFeedbackDTO> getIncompleteFeedback(Pageable pageable);

    /**
     * Check if a reviewer has already provided feedback for a proposal.
     *
     * @param proposalId the ID of the proposal
     * @param reviewerId the ID of the reviewer
     * @return true if feedback exists
     */
    boolean hasFeedback(Long proposalId, String reviewerId);

    /**
     * Submit final review for a feedback.
     *
     * @param id the ID of the feedback
     * @return the updated feedback with final review status
     */
    ProposalFeedbackDTO submitFinalReview(Long id);

    /**
     * Get feedback statistics grouped by department.
     *
     * @return map of department to average score
     */
    Map<String, Double> getFeedbackStatisticsByDepartment();

    /**
     * Get final review feedback for a specific department.
     *
     * @param department the department name
     * @return the list of final review feedback
     */
    List<ProposalFeedbackDTO> getFinalReviewFeedbackByDepartment(String department);

    /**
     * Mark a feedback as final review.
     *
     * @param id the ID of the feedback
     * @return the updated feedback
     */
    ProposalFeedbackDTO markAsFinalReview(Long id);

    /**
     * Get average scores by evaluation criteria.
     *
     * @return map of criteria to average score
     */
    Map<String, Double> getAverageScoresByCriteria();

    /**
     * Get feedback trends over a time period.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return map of criteria to list of scores over time
     */
    Map<String, List<Double>> getFeedbackTrends(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Calculate overall score for a feedback.
     *
     * @param feedbackDTO the feedback to calculate score for
     * @return the calculated overall score
     */
    Double calculateOverallScore(ProposalFeedbackDTO feedbackDTO);

    /**
     * Validate feedback data.
     *
     * @param feedbackDTO the feedback to validate
     * @throws IllegalArgumentException if validation fails
     */
    void validateFeedback(ProposalFeedbackDTO feedbackDTO);

    /**
     * Check if a feedback can be marked as final review.
     *
     * @param id the ID of the feedback
     * @return true if the feedback can be marked as final review
     */
    boolean canBeMarkedAsFinalReview(Long id);
} 