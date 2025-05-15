package com.skcc.ags.audit.controller;

import com.skcc.ags.audit.domain.AuditStatus;
import com.skcc.ags.audit.domain.AuditType;
import com.skcc.ags.audit.dto.AuditRecordDTO;
import com.skcc.ags.audit.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing audit records.
 */
@RestController
@RequestMapping("/api/v1/audit-records")
public class AuditController {
    private static final Logger log = LoggerFactory.getLogger(AuditController.class);
    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<AuditRecordDTO> createAuditRecord(
        @RequestBody AuditRecordDTO auditRecordDTO
    ) {
        log.debug("REST request to create audit record: {}", auditRecordDTO);
        AuditRecordDTO result = auditService.createAuditRecord(auditRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<AuditRecordDTO> updateAuditRecord(
        @PathVariable Long id,
        @RequestBody AuditRecordDTO auditRecordDTO
    ) {
        log.debug("REST request to update audit record {}: {}", id, auditRecordDTO);
        AuditRecordDTO result = auditService.updateAuditRecord(id, auditRecordDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_USER')")
    public ResponseEntity<AuditRecordDTO> getAuditRecord(@PathVariable Long id) {
        log.debug("REST request to get audit record: {}", id);
        Optional<AuditRecordDTO> result = auditService.getAuditRecord(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAuditRecord(@PathVariable Long id) {
        log.debug("REST request to delete audit record: {}", id);
        auditService.deleteAuditRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_USER')")
    public ResponseEntity<List<AuditRecordDTO>> getAuditRecordsByProject(
        @PathVariable Long projectId
    ) {
        log.debug("REST request to get audit records for project: {}", projectId);
        List<AuditRecordDTO> result = auditService.getAuditRecordsByProject(projectId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/personnel/{personnelId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_HR')")
    public ResponseEntity<List<AuditRecordDTO>> getAuditRecordsByPersonnel(
        @PathVariable String personnelId
    ) {
        log.debug("REST request to get audit records for personnel: {}", personnelId);
        List<AuditRecordDTO> result = auditService.getAuditRecordsByPersonnel(personnelId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/partner-company/{partnerCompany}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<AuditRecordDTO>> getAuditRecordsByPartnerCompany(
        @PathVariable String partnerCompany
    ) {
        log.debug("REST request to get audit records for partner company: {}", partnerCompany);
        List<AuditRecordDTO> result = auditService.getAuditRecordsByPartnerCompany(partnerCompany);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/type-status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<AuditRecordDTO>> getAuditRecordsByTypeAndStatus(
        @RequestParam AuditType type,
        @RequestParam AuditStatus status
    ) {
        log.debug("REST request to get audit records by type {} and status {}", type, status);
        List<AuditRecordDTO> result = auditService.getAuditRecordsByTypeAndStatus(type, status);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<AuditRecordDTO>> getAuditRecordsByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        log.debug("REST request to get audit records between {} and {}", startDate, endDate);
        List<AuditRecordDTO> result = auditService.getAuditRecordsByDateRange(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<AuditRecordDTO> updateAuditStatus(
        @PathVariable Long id,
        @RequestParam AuditStatus status
    ) {
        log.debug("REST request to update status to {} for audit record: {}", status, id);
        AuditRecordDTO result = auditService.updateAuditStatus(id, status);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/requiring-review")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<AuditRecordDTO>> getAuditRecordsRequiringDocumentReview() {
        log.debug("REST request to get audit records requiring document review");
        List<AuditRecordDTO> result = auditService.getAuditRecordsRequiringDocumentReview();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/missing-documents")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<AuditRecordDTO>> getAuditRecordsWithMissingDocuments() {
        log.debug("REST request to get audit records with missing documents");
        List<AuditRecordDTO> result = auditService.getAuditRecordsWithMissingDocuments();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_USER')")
    public ResponseEntity<List<AuditRecordDTO>> searchAuditRecords(
        @RequestParam(required = false) Long projectId,
        @RequestParam(required = false) String personnelId,
        @RequestParam(required = false) String partnerCompany,
        @RequestParam(required = false) String role,
        @RequestParam(required = false) Boolean active
    ) {
        log.debug("REST request to search audit records with criteria");
        List<AuditRecordDTO> result = auditService.searchAuditRecords(
            projectId, personnelId, partnerCompany, role, active);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<Long> getAuditRecordCountByStatus(@PathVariable AuditStatus status) {
        log.debug("REST request to get count of audit records with status: {}", status);
        long count = auditService.getAuditRecordCountByStatus(status);
        return ResponseEntity.ok(count);
    }
} 