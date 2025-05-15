package com.skcc.ags.audit.service.impl;

import com.skcc.ags.audit.domain.AuditRecord;
import com.skcc.ags.audit.domain.AuditStatus;
import com.skcc.ags.audit.domain.AuditType;
import com.skcc.ags.audit.dto.AuditRecordDTO;
import com.skcc.ags.audit.exception.AuditServiceException;
import com.skcc.ags.audit.mapper.AuditRecordMapper;
import com.skcc.ags.audit.repository.AuditRecordRepository;
import com.skcc.ags.audit.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the AuditService interface.
 */
@Service
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditRecordRepository auditRecordRepository;
    private final AuditRecordMapper auditRecordMapper;

    public AuditServiceImpl(AuditRecordRepository auditRecordRepository, AuditRecordMapper auditRecordMapper) {
        this.auditRecordRepository = auditRecordRepository;
        this.auditRecordMapper = auditRecordMapper;
    }

    @Override
    @Transactional
    public AuditRecordDTO createAuditRecord(AuditRecordDTO auditRecordDTO) {
        log.debug("Creating audit record: {}", auditRecordDTO);
        AuditRecord entity = auditRecordMapper.toEntity(auditRecordDTO);
        auditRecordRepository.save(entity);
        return auditRecordMapper.toDTO(entity);
    }

    @Override
    @Transactional
    public AuditRecordDTO updateAuditRecord(Long id, AuditRecordDTO auditRecordDTO) {
        log.debug("Updating audit record with id: {}", id);
        AuditRecord existingRecord = auditRecordRepository.findById(id)
            .orElseThrow(() -> new AuditServiceException("Audit record not found with id: " + id));
        AuditRecord updatedRecord = auditRecordMapper.updateEntityFromDTO(existingRecord, auditRecordDTO);
        auditRecordRepository.update(updatedRecord);
        return auditRecordMapper.toDTO(updatedRecord);
    }

    @Override
    public Optional<AuditRecordDTO> getAuditRecord(Long id) {
        log.debug("Getting audit record with id: {}", id);
        return auditRecordRepository.findById(id).map(auditRecordMapper::toDTO);
    }

    @Override
    public void deleteAuditRecord(Long id) {
        log.debug("Deleting audit record with id: {}", id);
        if (auditRecordRepository.findById(id).isEmpty()) {
            throw new AuditServiceException("Audit record not found with id: " + id);
        }
        auditRecordRepository.delete(id);
    }

    @Override
    public List<AuditRecordDTO> getAuditRecordsByProject(Long projectId) {
        log.debug("Getting audit records for project: {}", projectId);
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> r.getProjectId() != null && r.getProjectId().equals(projectId))
                .toList()
        );
    }

    @Override
    public List<AuditRecordDTO> getAuditRecordsByPersonnel(String personnelId) {
        log.debug("Getting audit records for personnel: {}", personnelId);
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> r.getPersonnelId() != null && r.getPersonnelId().equals(personnelId))
                .toList()
        );
    }

    @Override
    public List<AuditRecordDTO> getAuditRecordsByPartnerCompany(String partnerCompany) {
        log.debug("Getting audit records for partner company: {}", partnerCompany);
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> r.getPartnerCompany() != null && r.getPartnerCompany().equals(partnerCompany))
                .toList()
        );
    }

    @Override
    public List<AuditRecordDTO> getAuditRecordsByTypeAndStatus(AuditType type, AuditStatus status) {
        log.debug("Getting audit records by type: {} and status: {}", type, status);
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> r.getType() == type && r.getStatus() == status)
                .toList()
        );
    }

    @Override
    public List<AuditRecordDTO> getAuditRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Getting audit records between {} and {}", startDate, endDate);
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> r.getCreatedAt() != null && !r.getCreatedAt().isBefore(startDate) && !r.getCreatedAt().isAfter(endDate))
                .toList()
        );
    }

    @Override
    @Transactional
    public AuditRecordDTO updateAuditStatus(Long id, AuditStatus status) {
        log.debug("Updating status to {} for audit record: {}", status, id);
        AuditRecord record = auditRecordRepository.findById(id)
            .orElseThrow(() -> new AuditServiceException("Audit record not found with id: " + id));
        record.setStatus(status);
        auditRecordRepository.update(record);
        return auditRecordMapper.toDTO(record);
    }

    @Override
    public List<AuditRecordDTO> getAuditRecordsRequiringDocumentReview() {
        log.debug("Getting audit records requiring document review");
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> r.getStatus() == AuditStatus.IN_REVIEW)
                .toList()
        );
    }

    @Override
    public List<AuditRecordDTO> getAuditRecordsWithMissingDocuments() {
        log.debug("Getting audit records with missing required documents");
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> r.getStatus() == AuditStatus.PENDING_DOCUMENTS)
                .toList()
        );
    }

    @Override
    public List<AuditRecordDTO> searchAuditRecords(Long projectId, String personnelId, String partnerCompany, String role, Boolean active) {
        // 임시: findAll 후 stream/filter로 구현 (실제 쿼리는 추후 Mapper에 추가 필요)
        return auditRecordMapper.toDTOList(
            auditRecordRepository.findAll().stream()
                .filter(r -> projectId == null || (r.getProjectId() != null && r.getProjectId().equals(projectId)))
                .filter(r -> personnelId == null || (r.getPersonnelId() != null && r.getPersonnelId().equals(personnelId)))
                .filter(r -> partnerCompany == null || (r.getPartnerCompany() != null && r.getPartnerCompany().equals(partnerCompany)))
                // role, active 등 추가 필드가 있다면 여기에 필터 추가
                .toList()
        );
    }

    @Override
    public long getAuditRecordCountByStatus(AuditStatus status) {
        log.debug("Getting count of audit records with status: {}", status);
        return auditRecordRepository.findAll().stream()
            .filter(r -> r.getStatus() == status)
            .count();
    }

    @Override
    public List<AuditRecordDTO> findAll() {
        return auditRecordMapper.toDTOList(auditRecordRepository.findAll());
    }

    @Override
    public Optional<AuditRecordDTO> findById(Long id) {
        return auditRecordRepository.findById(id).map(auditRecordMapper::toDTO);
    }

    @Override
    public void save(AuditRecordDTO auditRecord) {
        auditRecordRepository.save(auditRecordMapper.toEntity(auditRecord));
    }

    @Override
    public void update(AuditRecordDTO auditRecord) {
        auditRecordRepository.update(auditRecordMapper.toEntity(auditRecord));
    }

    @Override
    public void delete(Long id) {
        auditRecordRepository.delete(id);
    }
} 