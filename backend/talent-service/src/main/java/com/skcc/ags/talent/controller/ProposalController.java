package com.skcc.ags.talent.controller;

import com.skcc.ags.talent.domain.ProposalStatus;
import com.skcc.ags.talent.dto.ProposalDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import com.skcc.ags.talent.service.ProposalService;
import com.skcc.ags.talent.service.ProposalStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for managing proposals
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/proposals")
@RequiredArgsConstructor
@Tag(name = "Proposal Management", description = "APIs for managing proposals")
public class ProposalController {

    private final ProposalService proposalService;

    @Operation(summary = "Create a new proposal")
    @PostMapping
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<ProposalDTO> createProposal(
            @Valid @RequestBody ProposalDTO proposalDTO) {
        log.info("REST request to create Proposal");
        ProposalDTO result = proposalService.createProposal(proposalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Update an existing proposal")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<ProposalDTO> updateProposal(
            @PathVariable Long id,
            @Valid @RequestBody ProposalDTO proposalDTO) {
        log.info("REST request to update Proposal : {}", id);
        ProposalDTO result = proposalService.updateProposal(id, proposalDTO);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get a proposal by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER', 'BUSINESS_TEAM')")
    public ResponseEntity<ProposalDTO> getProposal(@PathVariable Long id) {
        log.info("REST request to get Proposal : {}", id);
        return proposalService.getProposalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get proposals by project ID")
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('PM', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> getProposalsByProject(
            @PathVariable Long projectId,
            Pageable pageable) {
        log.info("REST request to get Proposals for Project : {}", projectId);
        Page<ProposalDTO> page = proposalService.getProposalsByProject(projectId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get proposals by candidate ID")
    @GetMapping("/candidate/{candidateId}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> getProposalsByCandidate(
            @PathVariable Long candidateId,
            Pageable pageable) {
        log.info("REST request to get Proposals for Candidate : {}", candidateId);
        Page<ProposalDTO> page = proposalService.getProposalsByCandidate(candidateId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get proposals by partner company ID")
    @GetMapping("/partner/{partnerCompanyId}")
    @PreAuthorize("hasAnyRole('PM', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> getProposalsByPartnerCompany(
            @PathVariable Long partnerCompanyId,
            Pageable pageable) {
        log.info("REST request to get Proposals for Partner Company : {}", partnerCompanyId);
        Page<ProposalDTO> page = proposalService.getProposalsByPartnerCompany(partnerCompanyId, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Submit a proposal for review")
    @PostMapping("/{id}/submit")
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<ProposalDTO> submitProposal(@PathVariable Long id) {
        log.info("REST request to submit Proposal : {}", id);
        ProposalDTO result = proposalService.submitProposal(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Add feedback to a proposal")
    @PostMapping("/{id}/feedback")
    @PreAuthorize("hasAnyRole('PM', 'BUSINESS_TEAM')")
    public ResponseEntity<ProposalFeedbackDTO> addFeedback(
            @PathVariable Long id,
            @Valid @RequestBody ProposalFeedbackDTO feedbackDTO) {
        log.info("REST request to add feedback to Proposal : {}", id);
        ProposalFeedbackDTO result = proposalService.addFeedback(id, feedbackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Update proposal status")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('PM')")
    public ResponseEntity<ProposalDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam ProposalStatus status,
            @RequestParam(required = false) String comment) {
        log.info("REST request to update status of Proposal : {} to {}", id, status);
        ProposalDTO result = proposalService.updateStatus(id, status, comment);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get proposals requiring review")
    @GetMapping("/review")
    @PreAuthorize("hasAnyRole('PM', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> getProposalsRequiringReview(Pageable pageable) {
        log.info("REST request to get Proposals requiring review");
        Page<ProposalDTO> page = proposalService.getProposalsRequiringReview(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get proposals by status")
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> getProposalsByStatus(
            @PathVariable ProposalStatus status,
            Pageable pageable) {
        log.info("REST request to get Proposals by status : {}", status);
        Page<ProposalDTO> page = proposalService.getProposalsByStatus(status, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Search proposals by keyword")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> searchProposals(
            @RequestParam String keyword,
            Pageable pageable) {
        log.info("REST request to search Proposals with keyword : {}", keyword);
        Page<ProposalDTO> page = proposalService.searchProposals(keyword, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get proposals expiring before a date")
    @GetMapping("/expiring")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER')")
    public ResponseEntity<Page<ProposalDTO>> getExpiringProposals(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime threshold,
            Pageable pageable) {
        log.info("REST request to get Proposals expiring before : {}", threshold);
        Page<ProposalDTO> page = proposalService.getExpiringProposals(threshold, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get high scoring proposals")
    @GetMapping("/high-scoring")
    @PreAuthorize("hasAnyRole('PM', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> getHighScoringProposals(
            @RequestParam Double minimumScore,
            Pageable pageable) {
        log.info("REST request to get high scoring Proposals");
        Page<ProposalDTO> page = proposalService.getHighScoringProposals(minimumScore, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get proposal statistics")
    @GetMapping("/statistics")
    @PreAuthorize("hasAnyRole('PM', 'BUSINESS_TEAM')")
    public ResponseEntity<ProposalStatistics> getProposalStatistics() {
        log.info("REST request to get Proposal statistics");
        ProposalStatistics statistics = proposalService.getProposalStatistics();
        return ResponseEntity.ok(statistics);
    }

    @Operation(summary = "Delete a proposal")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<Void> deleteProposal(@PathVariable Long id) {
        log.info("REST request to delete Proposal : {}", id);
        proposalService.deleteProposal(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all feedback for a proposal")
    @GetMapping("/{id}/feedback")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER', 'BUSINESS_TEAM')")
    public ResponseEntity<List<ProposalFeedbackDTO>> getProposalFeedback(@PathVariable Long id) {
        log.info("REST request to get feedback for Proposal : {}", id);
        List<ProposalFeedbackDTO> feedback = proposalService.getProposalFeedback(id);
        return ResponseEntity.ok(feedback);
    }

    @Operation(summary = "Get final review feedback for a proposal")
    @GetMapping("/{id}/feedback/final")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER', 'BUSINESS_TEAM')")
    public ResponseEntity<ProposalFeedbackDTO> getFinalReviewFeedback(@PathVariable Long id) {
        log.info("REST request to get final review feedback for Proposal : {}", id);
        return proposalService.getFinalReviewFeedback(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Recalculate scores for a proposal")
    @PostMapping("/{id}/recalculate-scores")
    @PreAuthorize("hasRole('PM')")
    public ResponseEntity<ProposalDTO> recalculateScores(@PathVariable Long id) {
        log.info("REST request to recalculate scores for Proposal : {}", id);
        ProposalDTO result = proposalService.recalculateScores(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Extend proposal expiration date")
    @PutMapping("/{id}/extend")
    @PreAuthorize("hasRole('PM')")
    public ResponseEntity<ProposalDTO> extendExpirationDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newExpirationDate) {
        log.info("REST request to extend expiration date for Proposal : {} to {}", id, newExpirationDate);
        ProposalDTO result = proposalService.extendExpirationDate(id, newExpirationDate);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get proposals by multiple statuses")
    @GetMapping("/by-statuses")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER', 'BUSINESS_TEAM')")
    public ResponseEntity<Page<ProposalDTO>> getProposalsByStatuses(
            @RequestParam List<ProposalStatus> statuses,
            Pageable pageable) {
        log.info("REST request to get Proposals by statuses : {}", statuses);
        Page<ProposalDTO> page = proposalService.getProposalsByStatuses(statuses, pageable);
        return ResponseEntity.ok(page);
    }
} 