package com.skcc.ags.talent.mapper;

import com.skcc.ags.talent.domain.Evaluation;
import com.skcc.ags.talent.dto.EvaluationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between Evaluation entities and DTOs
 */
@Component
public class EvaluationMapper {

    /**
     * Convert Evaluation entity to DTO
     * @param evaluation the evaluation entity
     * @return the evaluation DTO
     */
    public EvaluationDTO toDTO(Evaluation evaluation) {
        if (evaluation == null) {
            return null;
        }

        return EvaluationDTO.builder()
                .id(evaluation.getId())
                .candidateId(evaluation.getCandidate() != null ? evaluation.getCandidate().getId() : null)
                .projectId(evaluation.getProjectId())
                .evaluatorId(evaluation.getEvaluatorId())
                .evaluatorName(evaluation.getEvaluatorName())
                .evaluationType(evaluation.getEvaluationType())
                .technicalScore(evaluation.getTechnicalScore())
                .communicationScore(evaluation.getCommunicationScore())
                .problemSolvingScore(evaluation.getProblemSolvingScore())
                .teamFitScore(evaluation.getTeamFitScore())
                .overallScore(evaluation.getOverallScore())
                .strengths(evaluation.getStrengths())
                .weaknesses(evaluation.getWeaknesses())
                .comments(evaluation.getComments())
                .decision(evaluation.getDecision())
                .evaluationDate(evaluation.getEvaluationDate())
                .createdBy(evaluation.getCreatedBy())
                .createdAt(evaluation.getCreatedAt())
                .updatedBy(evaluation.getUpdatedBy())
                .updatedAt(evaluation.getUpdatedAt())
                .build();
    }

    /**
     * Convert Evaluation DTO to entity
     * @param dto the evaluation DTO
     * @return the evaluation entity
     */
    public Evaluation toEntity(EvaluationDTO dto) {
        if (dto == null) {
            return null;
        }

        return Evaluation.builder()
                .id(dto.getId())
                .projectId(dto.getProjectId())
                .evaluatorId(dto.getEvaluatorId())
                .evaluatorName(dto.getEvaluatorName())
                .evaluationType(dto.getEvaluationType())
                .technicalScore(dto.getTechnicalScore())
                .communicationScore(dto.getCommunicationScore())
                .problemSolvingScore(dto.getProblemSolvingScore())
                .teamFitScore(dto.getTeamFitScore())
                .overallScore(dto.getOverallScore())
                .strengths(dto.getStrengths())
                .weaknesses(dto.getWeaknesses())
                .comments(dto.getComments())
                .decision(dto.getDecision())
                .evaluationDate(dto.getEvaluationDate())
                .createdBy(dto.getCreatedBy())
                .createdAt(dto.getCreatedAt())
                .updatedBy(dto.getUpdatedBy())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    /**
     * Update an existing Evaluation entity with DTO data
     * @param evaluation the existing evaluation entity
     * @param dto the evaluation DTO with updated data
     */
    public void updateEntityFromDTO(Evaluation evaluation, EvaluationDTO dto) {
        if (evaluation == null || dto == null) {
            return;
        }

        evaluation.setProjectId(dto.getProjectId());
        evaluation.setEvaluatorId(dto.getEvaluatorId());
        evaluation.setEvaluatorName(dto.getEvaluatorName());
        evaluation.setEvaluationType(dto.getEvaluationType());
        evaluation.setTechnicalScore(dto.getTechnicalScore());
        evaluation.setCommunicationScore(dto.getCommunicationScore());
        evaluation.setProblemSolvingScore(dto.getProblemSolvingScore());
        evaluation.setTeamFitScore(dto.getTeamFitScore());
        evaluation.setOverallScore(dto.getOverallScore());
        evaluation.setStrengths(dto.getStrengths());
        evaluation.setWeaknesses(dto.getWeaknesses());
        evaluation.setComments(dto.getComments());
        evaluation.setDecision(dto.getDecision());
        evaluation.setEvaluationDate(dto.getEvaluationDate());
        evaluation.setUpdatedBy(dto.getUpdatedBy());
        evaluation.setUpdatedAt(dto.getUpdatedAt());
    }

    /**
     * Convert a list of Evaluation entities to DTOs
     * @param evaluations the list of evaluation entities
     * @return the list of evaluation DTOs
     */
    public List<EvaluationDTO> toDTOList(List<Evaluation> evaluations) {
        if (evaluations == null) {
            return new ArrayList<>();
        }
        return evaluations.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
} 