package com.ags.talent.service.impl;

import com.ags.talent.dto.CandidateEvaluationDTO;
import com.ags.talent.entity.Candidate;
import com.ags.talent.entity.Evaluation;
import com.ags.talent.exception.ResourceNotFoundException;
import com.ags.talent.mapper.EvaluationMapper;
import com.ags.talent.repository.CandidateRepository;
import com.ags.talent.repository.EvaluationRepository;
import com.ags.talent.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final CandidateRepository candidateRepository;
    private final EvaluationMapper evaluationMapper;

    @Override
    public List<CandidateEvaluationDTO> getEvaluationsByCandidateId(Long candidateId) {
        List<Evaluation> evaluations = evaluationRepository.findByCandidateId(candidateId);
        return evaluations.stream()
                .map(evaluationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateEvaluationDTO> getEvaluationsByCandidateIdAndStatus(Long candidateId, String status) {
        List<Evaluation> evaluations = evaluationRepository.findByCandidateIdAndStatus(candidateId, status);
        return evaluations.stream()
                .map(evaluationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateEvaluationDTO getEvaluationById(Long evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with id: " + evaluationId));
        return evaluationMapper.toDTO(evaluation);
    }

    @Override
    @Transactional
    public CandidateEvaluationDTO createEvaluation(Long candidateId, CandidateEvaluationDTO evaluationDTO) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + candidateId));

        // Check if evaluator has already evaluated this candidate
        if (evaluationRepository.existsByCandidateIdAndEvaluatorId(candidateId, evaluationDTO.getEvaluatorId())) {
            throw new IllegalStateException("Evaluator has already submitted an evaluation for this candidate");
        }

        Evaluation evaluation = evaluationMapper.toEntity(evaluationDTO);
        evaluation.setCandidate(candidate);
        evaluation.setEvaluationDate(LocalDateTime.now());
        evaluation = evaluationRepository.save(evaluation);

        return evaluationMapper.toDTO(evaluation);
    }

    @Override
    @Transactional
    public CandidateEvaluationDTO updateEvaluation(Long evaluationId, CandidateEvaluationDTO evaluationDTO) {
        Evaluation existingEvaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with id: " + evaluationId));

        evaluationMapper.updateEntityFromDTO(evaluationDTO, existingEvaluation);
        existingEvaluation = evaluationRepository.save(existingEvaluation);

        return evaluationMapper.toDTO(existingEvaluation);
    }

    @Override
    @Transactional
    public void deleteEvaluation(Long evaluationId) {
        if (!evaluationRepository.existsById(evaluationId)) {
            throw new ResourceNotFoundException("Evaluation not found with id: " + evaluationId);
        }
        evaluationRepository.deleteById(evaluationId);
    }
} 