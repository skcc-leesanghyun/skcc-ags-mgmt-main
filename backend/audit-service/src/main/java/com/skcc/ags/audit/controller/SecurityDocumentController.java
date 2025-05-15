package com.skcc.ags.audit.controller;

import com.skcc.ags.audit.domain.DocumentStatus;
import com.skcc.ags.audit.domain.SecurityDocumentType;
import com.skcc.ags.audit.dto.SecurityDocumentDTO;
import com.skcc.ags.audit.service.SecurityDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing security documents.
 */
@RestController
@RequestMapping("/api/v1/security-documents")
public class SecurityDocumentController {
    private static final Logger log = LoggerFactory.getLogger(SecurityDocumentController.class);
    private final SecurityDocumentService securityDocumentService;

    public SecurityDocumentController(SecurityDocumentService securityDocumentService) {
        this.securityDocumentService = securityDocumentService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<SecurityDocumentDTO> createDocument(
        @RequestParam Long auditRecordId,
        @RequestParam SecurityDocumentType documentType,
        @RequestParam MultipartFile file,
        @RequestParam(required = false) String comments,
        @RequestParam(required = false) LocalDateTime expiryDate
    ) {
        log.debug("REST request to create security document for audit record: {}", auditRecordId);
        SecurityDocumentDTO result = securityDocumentService.createDocument(
            auditRecordId, documentType, file, comments, expiryDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<SecurityDocumentDTO> updateDocument(
        @PathVariable Long id,
        @RequestBody SecurityDocumentDTO documentDTO
    ) {
        log.debug("REST request to update security document {}: {}", id, documentDTO);
        SecurityDocumentDTO result = securityDocumentService.updateDocument(id, documentDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_USER')")
    public ResponseEntity<SecurityDocumentDTO> getDocument(@PathVariable Long id) {
        log.debug("REST request to get security document: {}", id);
        SecurityDocumentDTO result = securityDocumentService.getDocument(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        log.debug("REST request to delete security document: {}", id);
        securityDocumentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/audit-record/{auditRecordId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_USER')")
    public ResponseEntity<List<SecurityDocumentDTO>> getDocumentsByAuditRecord(
        @PathVariable Long auditRecordId
    ) {
        log.debug("REST request to get documents for audit record: {}", auditRecordId);
        List<SecurityDocumentDTO> result = securityDocumentService.getDocumentsByAuditRecord(auditRecordId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/type/{documentType}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<SecurityDocumentDTO>> getDocumentsByType(
        @PathVariable SecurityDocumentType documentType
    ) {
        log.debug("REST request to get documents by type: {}", documentType);
        List<SecurityDocumentDTO> result = securityDocumentService.getDocumentsByType(documentType);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<SecurityDocumentDTO>> getDocumentsByStatus(
        @PathVariable DocumentStatus status
    ) {
        log.debug("REST request to get documents by status: {}", status);
        List<SecurityDocumentDTO> result = securityDocumentService.getDocumentsByStatus(status);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<SecurityDocumentDTO> updateDocumentStatus(
        @PathVariable Long id,
        @RequestParam DocumentStatus status
    ) {
        log.debug("REST request to update status to {} for document: {}", status, id);
        SecurityDocumentDTO result = securityDocumentService.updateDocumentStatus(id, status);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/expiring")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<SecurityDocumentDTO>> getExpiringDocuments(
        @RequestParam(defaultValue = "30") int daysThreshold
    ) {
        log.debug("REST request to get documents expiring within {} days", daysThreshold);
        List<SecurityDocumentDTO> result = securityDocumentService.getExpiringDocuments(daysThreshold);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/expired")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<List<SecurityDocumentDTO>> getExpiredDocuments() {
        log.debug("REST request to get expired documents");
        List<SecurityDocumentDTO> result = securityDocumentService.getExpiredDocuments();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR', 'ROLE_USER')")
    public ResponseEntity<List<SecurityDocumentDTO>> searchDocuments(
        @RequestParam(required = false) Long auditRecordId,
        @RequestParam(required = false) SecurityDocumentType documentType,
        @RequestParam(required = false) DocumentStatus status,
        @RequestParam(required = false) LocalDateTime fromDate,
        @RequestParam(required = false) LocalDateTime toDate
    ) {
        log.debug("REST request to search documents with criteria");
        List<SecurityDocumentDTO> result = securityDocumentService.searchDocuments(
            auditRecordId, documentType, status, fromDate, toDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count/status/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<Long> getDocumentCountByStatus(@PathVariable DocumentStatus status) {
        log.debug("REST request to get count of documents with status: {}", status);
        long count = securityDocumentService.getDocumentCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/type/{documentType}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<Long> getDocumentCountByType(@PathVariable SecurityDocumentType documentType) {
        log.debug("REST request to get count of documents with type: {}", documentType);
        long count = securityDocumentService.getDocumentCountByType(documentType);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/pending-required")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUDITOR')")
    public ResponseEntity<Long> getPendingRequiredDocumentsCount() {
        log.debug("REST request to get count of pending required documents");
        long count = securityDocumentService.getPendingRequiredDocumentsCount();
        return ResponseEntity.ok(count);
    }
} 