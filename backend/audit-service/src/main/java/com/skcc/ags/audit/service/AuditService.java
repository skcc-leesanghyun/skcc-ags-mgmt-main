package com.skcc.ags.audit.service;

import com.skcc.ags.audit.domain.AuditStatus;
import com.skcc.ags.audit.domain.AuditType;
import com.skcc.ags.audit.dto.AuditRecordDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing audit records.
 */
public interface AuditService {

    /**
     * Creates a new audit record.
     *
     * @param auditRecordDTO The audit record data
     * @return The created audit record
     */
    AuditRecordDTO createAuditRecord(AuditRecordDTO auditRecordDTO);

    /**
     * Updates an existing audit record.
     *
     * @param id The ID of the audit record to update
     * @param auditRecordDTO The updated audit record data
     * @return The updated audit record
     */
    AuditRecordDTO updateAuditRecord(Long id, AuditRecordDTO auditRecordDTO);

    /**
     * Retrieves an audit record by its ID.
     *
     * @param id The ID of the audit record
     * @return The audit record
     */
    Optional<AuditRecordDTO> getAuditRecord(Long id);

    /**
     * Deletes an audit record.
     *
     * @param id The ID of the audit record to delete
     */
    void deleteAuditRecord(Long id);

    /**
     * Retrieves audit records for a specific project.
     *
     * @param projectId The project ID
     * @return List of audit records
     */
    List<AuditRecordDTO> getAuditRecordsByProject(Long projectId);

    /**
     * Retrieves audit records for a specific personnel.
     *
     * @param personnelId The personnel ID
     * @return List of audit records
     */
    List<AuditRecordDTO> getAuditRecordsByPersonnel(String personnelId);

    /**
     * Retrieves audit records for a specific partner company with pagination.
     *
     * @param partnerCompany The partner company name
     * @return Page of audit records
     */
    List<AuditRecordDTO> getAuditRecordsByPartnerCompany(String partnerCompany);

    /**
     * Retrieves audit records by type and status.
     *
     * @param type The audit type
     * @param status The audit status
     * @return List of audit records
     */
    List<AuditRecordDTO> getAuditRecordsByTypeAndStatus(AuditType type, AuditStatus status);

    /**
     * Retrieves audit records created between specific dates.
     *
     * @param startDate Start date
     * @param endDate End date
     * @return List of audit records
     */
    List<AuditRecordDTO> getAuditRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Updates the status of an audit record.
     *
     * @param id The ID of the audit record
     * @param status The new status
     * @return The updated audit record
     */
    AuditRecordDTO updateAuditStatus(Long id, AuditStatus status);

    /**
     * Retrieves audit records that require document review.
     *
     * @return List of audit records
     */
    List<AuditRecordDTO> getAuditRecordsRequiringDocumentReview();

    /**
     * Retrieves audit records with missing required documents.
     *
     * @return List of audit records
     */
    List<AuditRecordDTO> getAuditRecordsWithMissingDocuments();

    /**
     * Searches audit records based on multiple criteria.
     *
     * @param projectId The project ID (optional)
     * @param personnelId The personnel ID (optional)
     * @param type The audit type (optional)
     * @param status The audit status (optional)
     * @param partnerCompany The partner company name (optional)
     * @param role The role (optional)
     * @param active The active status (optional)
     * @return List of audit records matching the criteria
     */
    List<AuditRecordDTO> searchAuditRecords(
        Long projectId,
        String personnelId,
        String partnerCompany,
        String role,
        Boolean active
    );

    /**
     * Gets the count of audit records by status.
     *
     * @param status The audit status
     * @return The count of audit records
     */
    long getAuditRecordCountByStatus(AuditStatus status);

    List<AuditRecordDTO> findAll();
    Optional<AuditRecordDTO> findById(Long id);
    void save(AuditRecordDTO auditRecord);
    void update(AuditRecordDTO auditRecord);
    void delete(Long id);
} 