package com.skcc.ags.talent.service.impl;

import com.skcc.ags.talent.domain.Proposal;
import com.skcc.ags.talent.domain.ProposalFeedback;
import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackStatisticsDTO;
import com.skcc.ags.talent.exception.InvalidFeedbackException;
import com.skcc.ags.talent.exception.ResourceNotFoundException;
import com.skcc.ags.talent.mapper.ProposalFeedbackMapper;
import com.skcc.ags.talent.repository.ProposalFeedbackRepository;
import com.skcc.ags.talent.repository.ProposalRepository;
import com.skcc.ags.talent.service.ProposalFeedbackService;
import com.skcc.ags.talent.service.ProposalFeedbackValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of ProposalFeedbackService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProposalFeedbackServiceImpl implements ProposalFeedbackService {

    private final ProposalFeedbackRepository feedbackRepository;
    private final ProposalRepository proposalRepository;
    private final ProposalFeedbackMapper feedbackMapper;
    private final ProposalFeedbackValidationService validationService;

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 100;
    private static final int LOW_SCORE_THRESHOLD = 60;
    private static final double TECHNICAL_WEIGHT = 0.4;
    private static final double EXPERIENCE_WEIGHT = 0.4;
    private static final double COST_WEIGHT = 0.2;
    private static final int ATTENTION_THRESHOLD = 5;

    @Override
    public ProposalFeedbackDTO createFeedback(ProposalFeedbackDTO feedbackDTO) {
        log.info("Creating new feedback for proposal: {}", feedbackDTO.getProposalId());
        validationService.validateForCreation(feedbackDTO);
        
        Proposal proposal = proposalRepository.findById(feedbackDTO.getProposalId())
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found with ID: " + feedbackDTO.getProposalId()));

        ProposalFeedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback.setProposal(proposal);
        feedback.setOverallScore(calculateOverallScore(feedbackDTO));
        
        ProposalFeedback savedFeedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDTO(savedFeedback);
    }

    @Override
    public ProposalFeedbackDTO updateFeedback(Long id, ProposalFeedbackDTO feedbackDTO) {
        log.info("Updating feedback with ID: {}", id);
        ProposalFeedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProposalFeedback", "id", id.toString()));

        validationService.validateForUpdate(feedbackDTO, existingFeedback);
        ProposalFeedback updatedFeedback = feedbackMapper.updateEntityFromDTO(existingFeedback, feedbackDTO);
        updatedFeedback.setOverallScore(calculateOverallScore(feedbackDTO));

        return feedbackMapper.toDTO(feedbackRepository.save(updatedFeedback));
    }

    @Override
    public void deleteFeedback(Long id) {
        log.info("Deleting feedback with ID: {}", id);
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("ProposalFeedback", "id", id.toString());
        }
        feedbackRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProposalFeedbackDTO getFeedbackById(Long id) {
        log.debug("Fetching feedback with ID: {}", id);
        return feedbackRepository.findById(id)
                .map(feedbackMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("ProposalFeedback", "id", id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalFeedbackDTO> getFeedbackByProposal(Long proposalId, Pageable pageable) {
        log.debug("Fetching feedback for proposal: {}", proposalId);
        return feedbackMapper.toDTOPage(feedbackRepository.findByProposalId(proposalId, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalFeedbackDTO> getFeedbackByReviewer(String reviewerId, Pageable pageable) {
        log.debug("Fetching feedback by reviewer: {}", reviewerId);
        return feedbackMapper.toDTOPage(feedbackRepository.findByReviewerId(reviewerId, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalFeedbackDTO> getFeedbackByDepartment(String department, Pageable pageable) {
        log.debug("Fetching feedback by department: {}", department);
        return feedbackMapper.toDTOPage(feedbackRepository.findByReviewerDepartment(department, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalFeedbackDTO> getHighScoringFeedback(Double minimumScore, Pageable pageable) {
        log.debug("Fetching high-scoring feedback with minimum score: {}", minimumScore);
        return feedbackMapper.toDTOPage(feedbackRepository.findHighScoringFeedback(minimumScore, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalFeedbackDTO> getFeedbackRequiringAttention(Integer threshold, Pageable pageable) {
        log.debug("Fetching feedback requiring attention with threshold: {}", threshold);
        return feedbackMapper.toDTOPage(feedbackRepository.findFeedbackRequiringAttention(threshold, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalFeedbackDTO> getFeedbackByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.debug("Fetching feedback between {} and {}", startDate, endDate);
        return feedbackMapper.toDTOPage(feedbackRepository.findByCreatedAtBetween(startDate, endDate, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public ProposalFeedbackDTO getLatestFeedback(Long proposalId) {
        log.debug("Fetching latest feedback for proposal: {}", proposalId);
        return feedbackRepository.findFirstByProposalIdOrderByCreatedAtDesc(proposalId)
                .map(feedbackMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("ProposalFeedback", "proposalId", proposalId.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public ProposalFeedbackStatisticsDTO getDepartmentStatistics(String department) {
        log.debug("Calculating statistics for department: {}", department);
        
        Map<String, Double> scores = feedbackRepository.calculateAverageScoresByDepartment(department);
        long totalFeedback = feedbackRepository.countByReviewerDepartmentAndCreatedAtBetween(
                department, LocalDateTime.now().minusMonths(1), LocalDateTime.now());
        long incompleteFeedback = feedbackRepository.findIncompleteFeeback(Pageable.unpaged()).getContent().size();
        long completedFeedback = totalFeedback - incompleteFeedback;
        long attentionRequired = feedbackRepository.findFeedbackRequiringAttention(ATTENTION_THRESHOLD, Pageable.unpaged())
                .getContent().size();
        long finalReviewCount = feedbackRepository.findByReviewerDepartmentAndFinalReviewIsTrue(department).size();

        return feedbackMapper.toStatisticsDTO(
                department, scores, totalFeedback, incompleteFeedback,
                completedFeedback, attentionRequired, finalReviewCount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProposalFeedbackDTO> getFinalReviewsByDepartment(String department) {
        log.debug("Fetching final reviews for department: {}", department);
        return feedbackMapper.toDTOList(
                feedbackRepository.findByReviewerDepartmentAndFinalReviewIsTrue(department));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalFeedbackDTO> getIncompleteFeedback(Pageable pageable) {
        log.debug("Fetching incomplete feedback");
        return feedbackMapper.toDTOPage(feedbackRepository.findIncompleteFeeback(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasFeedback(Long proposalId, String reviewerId) {
        log.debug("Checking if reviewer {} has feedback for proposal {}", reviewerId, proposalId);
        return feedbackRepository.existsByProposalIdAndReviewerId(proposalId, reviewerId);
    }

    @Override
    public ProposalFeedbackDTO submitFinalReview(Long id) {
        log.info("Submitting final review for feedback: {}", id);
        ProposalFeedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProposalFeedback", "id", id.toString()));

        if (!feedback.hasAllScores()) {
            throw InvalidFeedbackException.invalidFinalReview("All scores must be provided for final review");
        }

        feedback.setFinalReview(true);
        return feedbackMapper.toDTO(feedbackRepository.save(feedback));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Double> getFeedbackStatisticsByDepartment() {
        log.debug("Fetching feedback statistics by department");
        // Group feedback by department and calculate average scores
        return feedbackRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        ProposalFeedback::getReviewerDepartment,
                        Collectors.averagingDouble(ProposalFeedback::getOverallScore)
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProposalFeedbackDTO> getFinalReviewFeedbackByDepartment(String department) {
        log.debug("Fetching final review feedback for department: {}", department);
        return feedbackMapper.toDTOList(
                feedbackRepository.findByReviewerDepartmentAndFinalReviewIsTrue(department));
    }

    @Override
    public ProposalFeedbackDTO markAsFinalReview(Long id) {
        log.info("Marking feedback as final review: {}", id);
        return submitFinalReview(id); // Reuse existing functionality
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Double> getAverageScoresByCriteria() {
        log.debug("Calculating average scores by criteria");
        List<ProposalFeedback> allFeedback = feedbackRepository.findAll();
        
        Map<String, Double> averages = new HashMap<>();
        averages.put("technical", allFeedback.stream()
                .mapToDouble(ProposalFeedback::getTechnicalScore)
                .average()
                .orElse(0.0));
        averages.put("experience", allFeedback.stream()
                .mapToDouble(ProposalFeedback::getExperienceScore)
                .average()
                .orElse(0.0));
        averages.put("cost", allFeedback.stream()
                .mapToDouble(ProposalFeedback::getCostScore)
                .average()
                .orElse(0.0));
        averages.put("overall", allFeedback.stream()
                .mapToDouble(ProposalFeedback::getOverallScore)
                .average()
                .orElse(0.0));
                
        return averages;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<Double>> getFeedbackTrends(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Calculating feedback trends between {} and {}", startDate, endDate);
        List<ProposalFeedback> feedbackInRange = feedbackRepository.findByCreatedAtBetween(startDate, endDate, Pageable.unpaged())
                .getContent();

        Map<String, List<Double>> trends = new HashMap<>();
        trends.put("technical", feedbackInRange.stream()
                .map(ProposalFeedback::getTechnicalScore)
                .map(Double::valueOf)
                .collect(Collectors.toList()));
        trends.put("experience", feedbackInRange.stream()
                .map(ProposalFeedback::getExperienceScore)
                .map(Double::valueOf)
                .collect(Collectors.toList()));
        trends.put("cost", feedbackInRange.stream()
                .map(ProposalFeedback::getCostScore)
                .map(Double::valueOf)
                .collect(Collectors.toList()));
        trends.put("overall", feedbackInRange.stream()
                .map(ProposalFeedback::getOverallScore)
                .map(Double::valueOf)
                .collect(Collectors.toList()));

        return trends;
    }

    @Override
    public Double calculateOverallScore(ProposalFeedbackDTO feedbackDTO) {
        if (feedbackDTO.getTechnicalScore() == null ||
            feedbackDTO.getExperienceScore() == null ||
            feedbackDTO.getCostScore() == null) {
            return null;
        }

        return (feedbackDTO.getTechnicalScore() * TECHNICAL_WEIGHT) +
               (feedbackDTO.getExperienceScore() * EXPERIENCE_WEIGHT) +
               (feedbackDTO.getCostScore() * COST_WEIGHT);
    }

    @Override
    public void validateFeedback(ProposalFeedbackDTO feedbackDTO) {
        validationService.validateForCreation(feedbackDTO);
    }

    @Override
    public boolean canBeMarkedAsFinalReview(Long id) {
        ProposalFeedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProposalFeedback", "id", id.toString()));
        return feedback.hasAllScores() && !feedback.isFinalReview();
    }
} 