package com.skcc.ags.audit.service;

import com.skcc.ags.audit.domain.DocumentStatus;
import com.skcc.ags.audit.domain.SecurityDocumentType;
import com.skcc.ags.audit.dto.SecurityDocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing security documents.
 */
public interface SecurityDocumentService {

    /**
     * Creates a new security document with file upload.
     *
     * @param auditRecordId The ID of the associated audit record
     * @param documentType The type of the document
     * @param file The uploaded file
     * @param comments Optional comments about the document
     * @param expiryDate Optional expiry date for the document
     * @return The created security document
     */
    SecurityDocumentDTO createDocument(
        Long auditRecordId,
        SecurityDocumentType documentType,
        MultipartFile file,
        String comments,
        LocalDateTime expiryDate
    );

    /**
     * Updates an existing security document.
     *
     * @param id The ID of the document to update
     * @param documentDTO The updated document data
     * @return The updated security document
     */
    SecurityDocumentDTO updateDocument(Long id, SecurityDocumentDTO documentDTO);

    /**
     * Retrieves a security document by its ID.
     *
     * @param id The ID of the document
     * @return The security document
     */
    SecurityDocumentDTO getDocument(Long id);

    /**
     * Deletes a security document.
     *
     * @param id The ID of the document to delete
     */
    void deleteDocument(Long id);

    /**
     * Retrieves all documents associated with an audit record.
     *
     * @param auditRecordId The ID of the audit record
     * @return List of security documents
     */
    List<SecurityDocumentDTO> getDocumentsByAuditRecord(Long auditRecordId);

    /**
     * Retrieves documents by their type.
     *
     * @param documentType The type of documents to retrieve
     * @return List of security documents
     */
    List<SecurityDocumentDTO> getDocumentsByType(SecurityDocumentType documentType);

    /**
     * Retrieves documents by their status.
     *
     * @param status The status of documents to retrieve
     * @return List of security documents
     */
    List<SecurityDocumentDTO> getDocumentsByStatus(DocumentStatus status);

    /**
     * Updates the status of a document.
     *
     * @param id The ID of the document
     * @param status The new status
     * @return The updated security document
     */
    SecurityDocumentDTO updateDocumentStatus(Long id, DocumentStatus status);

    /**
     * Retrieves documents that are expiring soon.
     *
     * @param daysThreshold Number of days to consider for expiry
     * @return List of security documents
     */
    List<SecurityDocumentDTO> getExpiringDocuments(int daysThreshold);

    /**
     * Retrieves documents that have expired.
     *
     * @return List of security documents
     */
    List<SecurityDocumentDTO> getExpiredDocuments();

    /**
     * Searches documents based on multiple criteria.
     *
     * @param auditRecordId The audit record ID (optional)
     * @param documentType The document type (optional)
     * @param status The document status (optional)
     * @param fromDate The start date for search (optional)
     * @param toDate The end date for search (optional)
     * @return List of security documents matching the criteria
     */
    List<SecurityDocumentDTO> searchDocuments(
        Long auditRecordId,
        SecurityDocumentType documentType,
        DocumentStatus status,
        LocalDateTime fromDate,
        LocalDateTime toDate
    );

    /**
     * Gets the count of documents by status.
     *
     * @param status The document status
     * @return The count of documents
     */
    long getDocumentCountByStatus(DocumentStatus status);

    /**
     * Gets the count of documents by type.
     *
     * @param documentType The document type
     * @return The count of documents
     */
    long getDocumentCountByType(SecurityDocumentType documentType);

    /**
     * Gets the count of pending required documents.
     *
     * @return The count of pending required documents
     */
    long getPendingRequiredDocumentsCount();
} 