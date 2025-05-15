package com.skcc.ags.audit.mapper;

import com.skcc.ags.audit.domain.AuditRecord;
import com.skcc.ags.audit.domain.SecurityDocument;
import com.skcc.ags.audit.dto.SecurityDocumentDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between SecurityDocument entity and DTO objects.
 */
@Component
public class SecurityDocumentMapper {

    /**
     * Converts a SecurityDocument entity to its DTO representation.
     *
     * @param entity The SecurityDocument entity to convert
     * @return The corresponding DTO
     */
    public SecurityDocumentDTO toDTO(SecurityDocument entity) {
        if (entity == null) {
            return null;
        }

        SecurityDocumentDTO dto = SecurityDocumentDTO.builder()
            .id(entity.getId())
            .auditRecordId(entity.getAuditRecord() != null ? entity.getAuditRecord().getId() : null)
            .documentType(entity.getDocumentType())
            .fileName(entity.getFileName())
            .filePath(entity.getFilePath())
            .fileSize(entity.getFileSize())
            .mimeType(entity.getMimeType())
            .status(entity.getStatus())
            .comments(entity.getComments())
            .expiryDate(entity.getExpiryDate())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .createdBy(entity.getCreatedBy())
            .updatedBy(entity.getUpdatedBy())
            .required(entity.isRequired())
            .confidential(entity.isConfidential())
            .build();

        // Update display-related fields
        dto.updateExpiryStatus();
        dto.updateDisplayLabels();

        return dto;
    }

    /**
     * Converts a SecurityDocumentDTO to its entity representation.
     *
     * @param dto The DTO to convert
     * @param auditRecord The associated AuditRecord entity
     * @return The corresponding entity
     */
    public SecurityDocument toEntity(SecurityDocumentDTO dto, AuditRecord auditRecord) {
        if (dto == null) {
            return null;
        }

        return SecurityDocument.builder()
            .id(dto.getId())
            .auditRecord(auditRecord)
            .documentType(dto.getDocumentType())
            .fileName(dto.getFileName())
            .filePath(dto.getFilePath())
            .fileSize(dto.getFileSize())
            .mimeType(dto.getMimeType())
            .status(dto.getStatus())
            .comments(dto.getComments())
            .expiryDate(dto.getExpiryDate())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .createdBy(dto.getCreatedBy())
            .updatedBy(dto.getUpdatedBy())
            .required(dto.getRequired())
            .confidential(dto.getConfidential())
            .build();
    }

    /**
     * Overloaded toEntity method without AuditRecord parameter.
     *
     * @param dto The DTO to convert
     * @return The corresponding entity
     */
    public SecurityDocument toEntity(SecurityDocumentDTO dto) {
        return toEntity(dto, null);
    }

    /**
     * Converts a list of SecurityDocument entities to DTOs.
     *
     * @param entities The list of entities to convert
     * @return The list of corresponding DTOs
     */
    public List<SecurityDocumentDTO> toDTOList(List<SecurityDocument> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    /**
     * Updates an existing SecurityDocument entity with data from a DTO.
     *
     * @param entity The entity to update
     * @param dto The DTO containing the new data
     * @return The updated entity
     */
    public SecurityDocument updateEntityFromDTO(SecurityDocument entity, SecurityDocumentDTO dto) {
        if (entity == null || dto == null) {
            return entity;
        }

        // Update only non-null fields from DTO
        if (dto.getDocumentType() != null) entity.setDocumentType(dto.getDocumentType());
        if (dto.getFileName() != null) entity.setFileName(dto.getFileName());
        if (dto.getFilePath() != null) entity.setFilePath(dto.getFilePath());
        if (dto.getFileSize() != null) entity.setFileSize(dto.getFileSize());
        if (dto.getMimeType() != null) entity.setMimeType(dto.getMimeType());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getComments() != null) entity.setComments(dto.getComments());
        if (dto.getExpiryDate() != null) entity.setExpiryDate(dto.getExpiryDate());
        if (dto.getRequired() != null) entity.setRequired(dto.getRequired());
        if (dto.getConfidential() != null) entity.setConfidential(dto.getConfidential());
        if (dto.getUpdatedBy() != null) entity.setUpdatedBy(dto.getUpdatedBy());

        return entity;
    }
} 