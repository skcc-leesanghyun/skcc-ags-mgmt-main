package com.skcc.ags.audit.service.impl;

import com.skcc.ags.audit.constant.AuditConstants;
import com.skcc.ags.audit.domain.AuditRecord;
import com.skcc.ags.audit.domain.DocumentStatus;
import com.skcc.ags.audit.domain.SecurityDocument;
import com.skcc.ags.audit.domain.SecurityDocumentType;
import com.skcc.ags.audit.dto.SecurityDocumentDTO;
import com.skcc.ags.audit.exception.AuditServiceException;
import com.skcc.ags.audit.mapper.SecurityDocumentMapper;
import com.skcc.ags.audit.repository.AuditRecordRepository;
import com.skcc.ags.audit.repository.SecurityDocumentRepository;
import com.skcc.ags.audit.service.SecurityDocumentService;
import com.skcc.ags.audit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the SecurityDocumentService interface.
 */
@Service
@Transactional(readOnly = true)
public class SecurityDocumentServiceImpl implements SecurityDocumentService {
    private static final Logger log = LoggerFactory.getLogger(SecurityDocumentServiceImpl.class);
    private final SecurityDocumentRepository documentRepository;
    private final AuditRecordRepository auditRecordRepository;
    private final SecurityDocumentMapper documentMapper;
    private final FileUtils fileUtils;

    public SecurityDocumentServiceImpl(SecurityDocumentRepository documentRepository, AuditRecordRepository auditRecordRepository, SecurityDocumentMapper documentMapper, FileUtils fileUtils) {
        this.documentRepository = documentRepository;
        this.auditRecordRepository = auditRecordRepository;
        this.documentMapper = documentMapper;
        this.fileUtils = fileUtils;
    }

    @Override
    @Transactional
    public SecurityDocumentDTO createDocument(
        Long auditRecordId,
        SecurityDocumentType documentType,
        MultipartFile file,
        String comments,
        LocalDateTime expiryDate
    ) {
        log.debug("Creating security document for audit record: {}, type: {}", auditRecordId, documentType);

        AuditRecord auditRecord = auditRecordRepository.findById(auditRecordId)
            .orElseThrow(() -> new AuditServiceException("Audit record not found with id: " + auditRecordId));

        // Store the file and get the file path
        String filePath = fileUtils.storeFile(file, documentType.name());

        SecurityDocument document = SecurityDocument.builder()
            .auditRecordId(auditRecord.getId())
            .type(documentType)
            .fileName(file.getOriginalFilename())
            .filePath(filePath)
            .fileSize(file.getSize())
            .mimeType(file.getContentType())
            .status(DocumentStatus.SUBMITTED)
            .comments(comments)
            .expiryDate(expiryDate)
            .build();

        documentRepository.save(document);
        return documentMapper.toDTO(document);
    }

    @Override
    @Transactional
    public SecurityDocumentDTO updateDocument(Long id, SecurityDocumentDTO documentDTO) {
        log.debug("Updating security document with id: {}", id);
        SecurityDocument existingDocument = documentRepository.findById(id)
            .orElseThrow(() -> new AuditServiceException("Security document not found with id: " + id));

        SecurityDocument updatedDocument = documentMapper.updateEntityFromDTO(existingDocument, documentDTO);
        documentRepository.update(updatedDocument);
        return documentMapper.toDTO(updatedDocument);
    }

    @Override
    public SecurityDocumentDTO getDocument(Long id) {
        log.debug("Getting security document with id: {}", id);
        return documentRepository.findById(id)
            .map(documentMapper::toDTO)
            .orElseThrow(() -> new AuditServiceException("Security document not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteDocument(Long id) {
        log.debug("Deleting security document with id: {}", id);
        SecurityDocument document = documentRepository.findById(id)
            .orElseThrow(() -> new AuditServiceException("Security document not found with id: " + id));

        // Delete the physical file (임시 주석 처리, 실제 구현 필요)
        // fileUtils.deleteFile(document.getFilePath());

        documentRepository.delete(id);
    }

    @Override
    public List<SecurityDocumentDTO> getDocumentsByAuditRecord(Long auditRecordId) {
        log.debug("Getting documents for audit record: {}", auditRecordId);
        return documentMapper.toDTOList(documentRepository.findByAuditRecordId(auditRecordId));
    }

    @Override
    public List<SecurityDocumentDTO> getDocumentsByType(SecurityDocumentType documentType) {
        log.debug("Getting documents by type: {}", documentType);
        return documentMapper.toDTOList(
            documentRepository.findAll().stream()
                .filter(doc -> doc.getType() == documentType)
                .toList()
        );
    }

    @Override
    public List<SecurityDocumentDTO> getDocumentsByStatus(DocumentStatus status) {
        log.debug("Getting documents by status: {}", status);
        return documentMapper.toDTOList(
            documentRepository.findAll().stream()
                .filter(doc -> doc.getStatus() == status)
                .toList()
        );
    }

    @Override
    @Transactional
    public SecurityDocumentDTO updateDocumentStatus(Long id, DocumentStatus status) {
        log.debug("Updating status to {} for document: {}", status, id);
        SecurityDocument document = documentRepository.findById(id)
            .orElseThrow(() -> new AuditServiceException("Security document not found with id: " + id));

        document.setStatus(status);
        documentRepository.update(document);
        return documentMapper.toDTO(document);
    }

    @Override
    public List<SecurityDocumentDTO> getExpiringDocuments(int daysThreshold) {
        log.debug("Getting documents expiring within {} days", daysThreshold);
        LocalDateTime thresholdDate = LocalDateTime.now().plusDays(daysThreshold);
        return documentMapper.toDTOList(
            documentRepository.findAll().stream()
                .filter(doc -> doc.getExpiryDate() != null && doc.getExpiryDate().isBefore(thresholdDate))
                .toList()
        );
    }

    @Override
    public List<SecurityDocumentDTO> getExpiredDocuments() {
        log.debug("Getting expired documents");
        LocalDateTime now = LocalDateTime.now();
        return documentMapper.toDTOList(
            documentRepository.findAll().stream()
                .filter(doc -> doc.getExpiryDate() != null && doc.getExpiryDate().isBefore(now))
                .toList()
        );
    }

    @Override
    public List<SecurityDocumentDTO> searchDocuments(
        Long auditRecordId,
        SecurityDocumentType documentType,
        DocumentStatus status,
        LocalDateTime fromDate,
        LocalDateTime toDate
    ) {
        log.debug("Searching documents with criteria - auditRecordId: {}, type: {}, status: {}, dateRange: {} to {}",
            auditRecordId, documentType, status, fromDate, toDate);
        List<SecurityDocument> all = documentRepository.findAll();
        // 간단한 필터링 구현 (실제 쿼리는 추후 Mapper에 추가 필요)
        return documentMapper.toDTOList(
            all.stream()
                .filter(doc -> auditRecordId == null || doc.getAuditRecordId().equals(auditRecordId))
                .filter(doc -> documentType == null || doc.getType() == documentType)
                .filter(doc -> status == null || doc.getStatus() == status)
                .filter(doc -> fromDate == null || (doc.getSubmissionDate() != null && !doc.getSubmissionDate().isBefore(fromDate)))
                .filter(doc -> toDate == null || (doc.getSubmissionDate() != null && !doc.getSubmissionDate().isAfter(toDate)))
                .toList()
        );
    }

    @Override
    public long getDocumentCountByStatus(DocumentStatus status) {
        log.debug("Getting count of documents with status: {}", status);
        return documentRepository.findAll().stream()
            .filter(doc -> doc.getStatus() == status)
            .count();
    }

    @Override
    public long getDocumentCountByType(SecurityDocumentType documentType) {
        log.debug("Getting count of documents with type: {}", documentType);
        return documentRepository.findAll().stream()
            .filter(doc -> doc.getType() == documentType)
            .count();
    }

    @Override
    public long getPendingRequiredDocumentsCount() {
        log.debug("Getting count of pending required documents");
        return documentRepository.findAll().stream()
            .filter(doc -> doc.getStatus() == DocumentStatus.SUBMITTED)
            .count();
    }
} 