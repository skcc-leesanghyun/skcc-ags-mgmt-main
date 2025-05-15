package com.skcc.ags.talent.controller;

import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackStatisticsDTO;
import com.skcc.ags.talent.service.ProposalFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * REST controller for managing proposal feedback.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/proposal-feedback")
@RequiredArgsConstructor
@Validated
@Tag(name = "Proposal Feedback", description = "Proposal feedback management APIs")
public class ProposalFeedbackController {

    private final ProposalFeedbackService feedbackService;

    @Operation(summary = "Create new feedback for a proposal")
    @PostMapping
    @PreAuthorize("hasAnyRole('PM', 'REVIEWER')")
    public ResponseEntity<ProposalFeedbackDTO> createFeedback(
            @Valid @RequestBody ProposalFeedbackDTO feedbackDTO) {
        log.info("REST request to create feedback for proposal ID: {}", feedbackDTO.getProposalId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackService.createFeedback(feedbackDTO.getProposalId(), feedbackDTO));
    }

    @Operation(summary = "Update existing feedback")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PM', 'REVIEWER')")
    public ResponseEntity<ProposalFeedbackDTO> updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody ProposalFeedbackDTO feedbackDTO) {
        log.info("REST request to update feedback ID: {}", id);
        return ResponseEntity.ok(feedbackService.updateFeedback(id, feedbackDTO));
    }

    @Operation(summary = "Get feedback by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PM', 'REVIEWER', 'ADMIN')")
    public ResponseEntity<ProposalFeedbackDTO> getFeedback(@PathVariable Long id) {
        log.info("REST request to get feedback ID: {}", id);
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @Operation(summary = "Get all feedback for a proposal")
    @GetMapping("/proposal/{proposalId}")
    @PreAuthorize("hasAnyRole('PM', 'REVIEWER', 'ADMIN')")
    public ResponseEntity<Page<ProposalFeedbackDTO>> getFeedbackByProposal(
            @PathVariable Long proposalId,
            Pageable pageable) {
        log.info("REST request to get feedback for proposal ID: {}", proposalId);
        return ResponseEntity.ok(feedbackService.getFeedbackByProposalId(proposalId, pageable));
    }

    @Operation(summary = "Get feedback by reviewer")
    @GetMapping("/reviewer/{reviewerId}")
    @PreAuthorize("hasAnyRole('PM', 'ADMIN')")
    public ResponseEntity<Page<ProposalFeedbackDTO>> getFeedbackByReviewer(
            @PathVariable String reviewerId,
            Pageable pageable) {
        log.info("REST request to get feedback by reviewer ID: {}", reviewerId);
        return ResponseEntity.ok(feedbackService.getFeedbackByReviewer(reviewerId, pageable));
    }

    @Operation(summary = "Get high scoring feedback")
    @GetMapping("/high-scoring")
    @PreAuthorize("hasAnyRole('PM', 'ADMIN')")
    public ResponseEntity<Page<ProposalFeedbackDTO>> getHighScoringFeedback(
            @RequestParam(defaultValue = "90") Integer minimumScore,
            Pageable pageable) {
        log.info("REST request to get high scoring feedback (minimum score: {})", minimumScore);
        return ResponseEntity.ok(feedbackService.getHighScoringFeedback(minimumScore, pageable));
    }

    @Operation(summary = "Get feedback requiring attention")
    @GetMapping("/requiring-attention")
    @PreAuthorize("hasAnyRole('PM', 'ADMIN')")
    public ResponseEntity<Page<ProposalFeedbackDTO>> getFeedbackRequiringAttention(
            @RequestParam(defaultValue = "60") Integer threshold,
            Pageable pageable) {
        log.info("REST request to get feedback requiring attention (threshold: {})", threshold);
        return ResponseEntity.ok(feedbackService.getFeedbackRequiringAttention(threshold, pageable));
    }

    @Operation(summary = "Get feedback by date range")
    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('PM', 'ADMIN')")
    public ResponseEntity<Page<ProposalFeedbackDTO>> getFeedbackByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        log.info("REST request to get feedback between {} and {}", startDate, endDate);
        return ResponseEntity.ok(feedbackService.getFeedbackByDateRange(startDate, endDate, pageable));
    }

    @Operation(summary = "Get feedback statistics by department")
    @GetMapping("/statistics/{department}")
    @PreAuthorize("hasAnyRole('PM', 'ADMIN')")
    public ResponseEntity<ProposalFeedbackStatisticsDTO> getFeedbackStatistics(
            @PathVariable String department) {
        log.info("REST request to get feedback statistics for department: {}", department);
        return ResponseEntity.ok(feedbackService.getFeedbackStatistics(department));
    }

    @Operation(summary = "Delete feedback")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.info("REST request to delete feedback ID: {}", id);
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }
} 