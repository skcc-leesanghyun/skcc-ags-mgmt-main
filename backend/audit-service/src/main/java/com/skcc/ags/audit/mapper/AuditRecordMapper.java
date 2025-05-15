package com.skcc.ags.audit.mapper;

import com.skcc.ags.audit.domain.AuditRecord;
import com.skcc.ags.audit.dto.AuditRecordDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between AuditRecord entity and DTO objects.
 */
@Component
public class AuditRecordMapper {
    private final SecurityDocumentMapper securityDocumentMapper;

    public AuditRecordMapper(SecurityDocumentMapper securityDocumentMapper) {
        this.securityDocumentMapper = securityDocumentMapper;
    }

    /**
     * Converts an AuditRecord entity to its DTO representation.
     *
     * @param entity The AuditRecord entity to convert
     * @return The corresponding DTO
     */
    public AuditRecordDTO toDTO(AuditRecord entity) {
        if (entity == null) {
            return null;
        }

        AuditRecordDTO dto = AuditRecordDTO.builder()
            .id(entity.getId())
            .projectId(entity.getProjectId())
            .personnelId(entity.getPersonnelId())
            .partnerCompany(entity.getPartnerCompany())
            .type(entity.getType())
            .status(entity.getStatus())
            .notes(entity.getNotes())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .createdBy(entity.getCreatedBy())
            .updatedBy(entity.getUpdatedBy())
            .build();

        // Convert and set documents
        if (entity.getDocuments() != null) {
            dto.setDocuments(entity.getDocuments().stream()
                .map(securityDocumentMapper::toDTO)
                .collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Converts an AuditRecordDTO to its entity representation.
     *
     * @param dto The DTO to convert
     * @return The corresponding entity
     */
    public AuditRecord toEntity(AuditRecordDTO dto) {
        if (dto == null) {
            return null;
        }

        AuditRecord entity = AuditRecord.builder()
            .id(dto.getId())
            .projectId(dto.getProjectId())
            .personnelId(dto.getPersonnelId())
            .partnerCompany(dto.getPartnerCompany())
            .type(dto.getType())
            .status(dto.getStatus())
            .notes(dto.getNotes())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .createdBy(dto.getCreatedBy())
            .updatedBy(dto.getUpdatedBy())
            .build();

        // Convert and set documents
        if (dto.getDocuments() != null) {
            entity.setDocuments(dto.getDocuments().stream()
                .map(docDTO -> {
                    var doc = securityDocumentMapper.toEntity(docDTO);
                    doc.setAuditRecord(entity);
                    return doc;
                })
                .collect(Collectors.toList()));
        }

        return entity;
    }

    /**
     * Converts a list of AuditRecord entities to DTOs.
     *
     * @param entities The list of entities to convert
     * @return The list of corresponding DTOs
     */
    public List<AuditRecordDTO> toDTOList(List<AuditRecord> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Updates an existing AuditRecord entity with data from a DTO.
     *
     * @param entity The entity to update
     * @param dto The DTO containing the new data
     * @return The updated entity
     */
    public AuditRecord updateEntityFromDTO(AuditRecord entity, AuditRecordDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }

        // Update only non-null fields from DTO
        if (dto.getProjectId() != null) entity.setProjectId(dto.getProjectId());
        if (dto.getPersonnelId() != null) entity.setPersonnelId(dto.getPersonnelId());
        if (dto.getPartnerCompany() != null) entity.setPartnerCompany(dto.getPartnerCompany());
        if (dto.getType() != null) entity.setType(dto.getType());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getNotes() != null) entity.setNotes(dto.getNotes());
        if (dto.getUpdatedBy() != null) entity.setUpdatedBy(dto.getUpdatedBy());

        return entity;
    }
} 