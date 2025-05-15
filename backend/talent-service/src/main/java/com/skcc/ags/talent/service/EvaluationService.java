package com.ags.talent.service;

import com.ags.talent.dto.EvaluationDTO;
import com.ags.talent.dto.EvaluationFilterDTO;

import java.util.List;

public interface EvaluationService {
    List<EvaluationDTO> getEvaluations(EvaluationFilterDTO filters);
    List<String> getEvaluators();
    EvaluationDTO updateEvaluation(Long id, EvaluationDTO evaluationDTO);
} 