package com.skcc.ags.talent.service;

import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import com.skcc.ags.talent.entity.ProposalFeedback;
import com.skcc.ags.talent.exception.InvalidFeedbackException;
import com.skcc.ags.talent.repository.ProposalFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for validating proposal feedback.
 */
@Service
@RequiredArgsConstructor
public class ProposalFeedbackValidationService {

    private final ProposalFeedbackRepository feedbackRepository;

    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 10;
    private static final int MIN_COMMENTS_LENGTH = 10;
    private static final int MAX_COMMENTS_LENGTH = 1000;
    private static final int ATTENTION_THRESHOLD = 5;

    /**
     * Validate feedback data for creation.
     *
     * @param feedbackDTO the feedback data to validate
     * @throws InvalidFeedbackException if validation fails
     */
    public void validateForCreation(ProposalFeedbackDTO feedbackDTO) {
        validateRequiredFields(feedbackDTO);
        validateScores(feedbackDTO);
        validateComments(feedbackDTO);
        validateDuplicateFeedback(feedbackDTO);
    }

    /**
     * Validate feedback data for update.
     *
     * @param feedbackDTO the feedback data to validate
     * @param existingFeedback the existing feedback entity
     * @throws InvalidFeedbackException if validation fails
     */
    public void validateForUpdate(ProposalFeedbackDTO feedbackDTO, ProposalFeedback existingFeedback) {
        if (feedbackDTO.getTechnicalScore() != null) {
            validateScore(feedbackDTO.getTechnicalScore(), "technicalScore");
        }
        if (feedbackDTO.getExperienceScore() != null) {
            validateScore(feedbackDTO.getExperienceScore(), "experienceScore");
        }
        if (feedbackDTO.getCostScore() != null) {
            validateScore(feedbackDTO.getCostScore(), "costScore");
        }
        if (feedbackDTO.getComments() != null) {
            validateCommentsLength(feedbackDTO.getComments());
        }
        validateFinalReviewEligibility(feedbackDTO, existingFeedback);
    }

    /**
     * Validate required fields are present.
     *
     * @param feedbackDTO the feedback data to validate
     * @throws InvalidFeedbackException if required fields are missing
     */
    private void validateRequiredFields(ProposalFeedbackDTO feedbackDTO) {
        if (feedbackDTO.getProposalId() == null) {
            throw InvalidFeedbackException.missingRequiredField("proposalId");
        }
        if (feedbackDTO.getReviewerId() == null || feedbackDTO.getReviewerId().trim().isEmpty()) {
            throw InvalidFeedbackException.missingRequiredField("reviewerId");
        }
        if (feedbackDTO.getReviewerName() == null || feedbackDTO.getReviewerName().trim().isEmpty()) {
            throw InvalidFeedbackException.missingRequiredField("reviewerName");
        }
        if (feedbackDTO.getReviewerDepartment() == null || feedbackDTO.getReviewerDepartment().trim().isEmpty()) {
            throw InvalidFeedbackException.missingRequiredField("reviewerDepartment");
        }
    }

    /**
     * Validate all scores are within acceptable range.
     *
     * @param feedbackDTO the feedback data to validate
     * @throws InvalidFeedbackException if scores are invalid
     */
    private void validateScores(ProposalFeedbackDTO feedbackDTO) {
        validateScore(feedbackDTO.getTechnicalScore(), "technicalScore");
        validateScore(feedbackDTO.getExperienceScore(), "experienceScore");
        validateScore(feedbackDTO.getCostScore(), "costScore");
    }

    /**
     * Validate a single score is within acceptable range.
     *
     * @param score the score to validate
     * @param fieldName the name of the score field
     * @throws InvalidFeedbackException if score is invalid
     */
    private void validateScore(Integer score, String fieldName) {
        if (score == null) {
            throw InvalidFeedbackException.missingRequiredField(fieldName);
        }
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw InvalidFeedbackException.invalidScore(fieldName, MIN_SCORE, MAX_SCORE);
        }
    }

    /**
     * Validate comments field.
     *
     * @param feedbackDTO the feedback data to validate
     * @throws InvalidFeedbackException if comments are invalid
     */
    private void validateComments(ProposalFeedbackDTO feedbackDTO) {
        String comments = feedbackDTO.getComments();
        if (comments == null || comments.trim().isEmpty()) {
            throw InvalidFeedbackException.missingRequiredField("comments");
        }
        validateCommentsLength(comments);
    }

    /**
     * Validate comments length is within acceptable range.
     *
     * @param comments the comments to validate
     * @throws InvalidFeedbackException if comments length is invalid
     */
    private void validateCommentsLength(String comments) {
        int length = comments.trim().length();
        if (length < MIN_COMMENTS_LENGTH || length > MAX_COMMENTS_LENGTH) {
            throw InvalidFeedbackException.invalidLength("comments", MIN_COMMENTS_LENGTH, MAX_COMMENTS_LENGTH);
        }
    }

    /**
     * Check for duplicate feedback from the same reviewer.
     *
     * @param feedbackDTO the feedback data to validate
     * @throws InvalidFeedbackException if duplicate feedback exists
     */
    private void validateDuplicateFeedback(ProposalFeedbackDTO feedbackDTO) {
        if (feedbackRepository.existsByProposalIdAndReviewerId(
                feedbackDTO.getProposalId(), feedbackDTO.getReviewerId())) {
            throw new InvalidFeedbackException(
                "reviewerId",
                "Duplicate feedback",
                "Reviewer has already provided feedback for this proposal"
            );
        }
    }

    /**
     * Validate eligibility for final review.
     *
     * @param feedbackDTO the feedback data to validate
     * @param existingFeedback the existing feedback entity
     * @throws InvalidFeedbackException if final review criteria are not met
     */
    private void validateFinalReviewEligibility(ProposalFeedbackDTO feedbackDTO, ProposalFeedback existingFeedback) {
        if (feedbackDTO.isFinalReview() && !existingFeedback.hasAllScores()) {
            throw InvalidFeedbackException.invalidFinalReview("All scores must be provided for final review");
        }
    }

    /**
     * Check if feedback needs attention based on scores.
     *
     * @param feedbackDTO the feedback to check
     * @return true if any score is below the attention threshold
     */
    public boolean needsAttention(ProposalFeedbackDTO feedbackDTO) {
        return (feedbackDTO.getTechnicalScore() != null && feedbackDTO.getTechnicalScore() < ATTENTION_THRESHOLD) ||
               (feedbackDTO.getExperienceScore() != null && feedbackDTO.getExperienceScore() < ATTENTION_THRESHOLD) ||
               (feedbackDTO.getCostScore() != null && feedbackDTO.getCostScore() < ATTENTION_THRESHOLD);
    }
} 