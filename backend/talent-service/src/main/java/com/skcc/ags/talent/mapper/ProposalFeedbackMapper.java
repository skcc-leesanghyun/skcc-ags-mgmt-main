package com.skcc.ags.talent.mapper;

import com.skcc.ags.talent.domain.ProposalFeedback;
import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackStatisticsDTO;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper for converting between ProposalFeedback entities and DTOs.
 */
@Component
public class ProposalFeedbackMapper {

    /**
     * Convert a ProposalFeedback entity to a DTO.
     *
     * @param feedback the entity to convert
     * @return the converted DTO
     */
    public ProposalFeedbackDTO toDTO(ProposalFeedback feedback) {
        if (feedback == null) {
            return null;
        }

        return ProposalFeedbackDTO.builder()
                .id(feedback.getId())
                .proposalId(feedback.getProposalId())
                .reviewerId(feedback.getReviewerId())
                .reviewerName(feedback.getReviewerName())
                .reviewerDepartment(feedback.getReviewerDepartment())
                .comments(feedback.getComments())
                .technicalScore(feedback.getTechnicalScore())
                .experienceScore(feedback.getExperienceScore())
                .costScore(feedback.getCostScore())
                .overallScore(feedback.getOverallScore())
                .strengths(feedback.getStrengths())
                .areasForImprovement(feedback.getAreasForImprovement())
                .requirements(feedback.getRequirements())
                .recommendation(feedback.getRecommendation())
                .additionalNotes(feedback.getAdditionalNotes())
                .finalReview(feedback.isFinalReview())
                .createdAt(feedback.getCreatedAt())
                .createdBy(feedback.getCreatedBy())
                .modifiedAt(feedback.getModifiedAt())
                .modifiedBy(feedback.getModifiedBy())
                .build();
    }

    /**
     * Convert a ProposalFeedbackDTO to an entity.
     *
     * @param dto the DTO to convert
     * @return the converted entity
     */
    public ProposalFeedback toEntity(ProposalFeedbackDTO dto) {
        if (dto == null) {
            return null;
        }

        return ProposalFeedback.builder()
                .id(dto.getId())
                .proposalId(dto.getProposalId())
                .reviewerId(dto.getReviewerId())
                .reviewerName(dto.getReviewerName())
                .reviewerDepartment(dto.getReviewerDepartment())
                .comments(dto.getComments())
                .technicalScore(dto.getTechnicalScore())
                .experienceScore(dto.getExperienceScore())
                .costScore(dto.getCostScore())
                .overallScore(dto.getOverallScore())
                .strengths(dto.getStrengths())
                .areasForImprovement(dto.getAreasForImprovement())
                .requirements(dto.getRequirements())
                .recommendation(dto.getRecommendation())
                .additionalNotes(dto.getAdditionalNotes())
                .finalReview(dto.isFinalReview())
                .build();
    }

    /**
     * Update a ProposalFeedback entity with data from a DTO.
     *
     * @param existingFeedback the existing entity to update
     * @param dto the DTO containing new values
     * @return the updated entity
     */
    public ProposalFeedback updateEntityFromDTO(ProposalFeedback existingFeedback, ProposalFeedbackDTO dto) {
        if (dto.getComments() != null) {
            existingFeedback.setComments(dto.getComments());
        }
        if (dto.getTechnicalScore() != null) {
            existingFeedback.setTechnicalScore(dto.getTechnicalScore());
        }
        if (dto.getExperienceScore() != null) {
            existingFeedback.setExperienceScore(dto.getExperienceScore());
        }
        if (dto.getCostScore() != null) {
            existingFeedback.setCostScore(dto.getCostScore());
        }
        if (dto.getStrengths() != null) {
            existingFeedback.setStrengths(dto.getStrengths());
        }
        if (dto.getAreasForImprovement() != null) {
            existingFeedback.setAreasForImprovement(dto.getAreasForImprovement());
        }
        if (dto.getRequirements() != null) {
            existingFeedback.setRequirements(dto.getRequirements());
        }
        if (dto.getRecommendation() != null) {
            existingFeedback.setRecommendation(dto.getRecommendation());
        }
        if (dto.getAdditionalNotes() != null) {
            existingFeedback.setAdditionalNotes(dto.getAdditionalNotes());
        }
        existingFeedback.setFinalReview(dto.isFinalReview());
        
        return existingFeedback;
    }

    /**
     * Convert a list of ProposalFeedback entities to DTOs.
     *
     * @param feedbackList the list of entities
     * @return the list of DTOs
     */
    public List<ProposalFeedbackDTO> toDTOList(List<ProposalFeedback> feedbackList) {
        return feedbackList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert a Page of ProposalFeedback entities to DTOs.
     *
     * @param page the page of entities
     * @return the page of DTOs
     */
    public Page<ProposalFeedbackDTO> toDTOPage(Page<ProposalFeedback> page) {
        return page.map(this::toDTO);
    }

    /**
     * Create statistics DTO from department scores and counts.
     *
     * @param department the department name
     * @param scores the average scores map
     * @param totalFeedback total feedback count
     * @param pendingFeedback pending feedback count
     * @param completedFeedback completed feedback count
     * @param attentionRequired feedback requiring attention count
     * @param finalReviewCount final review count
     * @return the statistics DTO
     */
    public ProposalFeedbackStatisticsDTO toStatisticsDTO(
            String department,
            Map<String, Double> scores,
            long totalFeedback,
            long pendingFeedback,
            long completedFeedback,
            long attentionRequired,
            long finalReviewCount) {
        
        return ProposalFeedbackStatisticsDTO.builder()
                .department(department)
                .averageScore(scores.get("overallAvg"))
                .totalFeedback(totalFeedback)
                .pendingFeedback(pendingFeedback)
                .completedFeedback(completedFeedback)
                .technicalScoreAverage(scores.get("technicalAvg"))
                .experienceScoreAverage(scores.get("experienceAvg"))
                .costScoreAverage(scores.get("costAvg"))
                .feedbackRequiringAttention(attentionRequired)
                .finalReviewCount(finalReviewCount)
                .build();
    }
} 