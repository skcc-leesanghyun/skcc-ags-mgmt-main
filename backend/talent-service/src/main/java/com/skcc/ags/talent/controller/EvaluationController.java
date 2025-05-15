package com.ags.talent.controller;

import com.ags.talent.dto.CandidateEvaluationDTO;
import com.ags.talent.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping("/candidates/{candidateId}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER')")
    public ResponseEntity<List<CandidateEvaluationDTO>> getEvaluationsByCandidateId(
            @PathVariable Long candidateId) {
        return ResponseEntity.ok(evaluationService.getEvaluationsByCandidateId(candidateId));
    }

    @GetMapping("/candidates/{candidateId}/status/{status}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER')")
    public ResponseEntity<List<CandidateEvaluationDTO>> getEvaluationsByCandidateIdAndStatus(
            @PathVariable Long candidateId,
            @PathVariable String status) {
        return ResponseEntity.ok(evaluationService.getEvaluationsByCandidateIdAndStatus(candidateId, status));
    }

    @GetMapping("/{evaluationId}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER')")
    public ResponseEntity<CandidateEvaluationDTO> getEvaluationById(
            @PathVariable Long evaluationId) {
        return ResponseEntity.ok(evaluationService.getEvaluationById(evaluationId));
    }

    @PostMapping("/candidates/{candidateId}")
    @PreAuthorize("hasRole('PM')")
    public ResponseEntity<CandidateEvaluationDTO> createEvaluation(
            @PathVariable Long candidateId,
            @Valid @RequestBody CandidateEvaluationDTO evaluationDTO) {
        return ResponseEntity.ok(evaluationService.createEvaluation(candidateId, evaluationDTO));
    }

    @PutMapping("/{evaluationId}")
    @PreAuthorize("hasRole('PM')")
    public ResponseEntity<CandidateEvaluationDTO> updateEvaluation(
            @PathVariable Long evaluationId,
            @Valid @RequestBody CandidateEvaluationDTO evaluationDTO) {
        return ResponseEntity.ok(evaluationService.updateEvaluation(evaluationId, evaluationDTO));
    }

    @DeleteMapping("/{evaluationId}")
    @PreAuthorize("hasRole('PM')")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long evaluationId) {
        evaluationService.deleteEvaluation(evaluationId);
        return ResponseEntity.ok().build();
    }
} 