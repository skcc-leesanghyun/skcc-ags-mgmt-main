package com.skcc.ags.talent.mapper;

import com.skcc.ags.talent.domain.Candidate;
import com.skcc.ags.talent.domain.CandidateFile;
import com.skcc.ags.talent.domain.Evaluation;
import com.skcc.ags.talent.dto.CandidateDTO;
import com.skcc.ags.talent.dto.CandidateFileDTO;
import com.skcc.ags.talent.dto.EvaluationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between Candidate entities and DTOs
 */
@Component
@RequiredArgsConstructor
public class CandidateMapper {

    private final EvaluationMapper evaluationMapper;

    /**
     * Convert Candidate entity to DTO
     * @param candidate the candidate entity
     * @return the candidate DTO
     */
    public CandidateDTO toDTO(Candidate candidate) {
        if (candidate == null) {
            return null;
        }

        return CandidateDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .englishName(candidate.getEnglishName())
                .email(candidate.getEmail())
                .phoneNumber(candidate.getPhoneNumber())
                .partnerCompany(candidate.getPartnerCompany())
                .yearsOfExperience(candidate.getYearsOfExperience())
                .technicalGrade(candidate.getTechnicalGrade())
                .skills(candidate.getSkills())
                .experience(candidate.getExperience())
                .availabilityDate(candidate.getAvailabilityDate())
                .currentLocation(candidate.getCurrentLocation())
                .preferredLocation(candidate.getPreferredLocation())
                .noticePeriod(candidate.getNoticePeriod())
                .expectedSalary(candidate.getExpectedSalary())
                .resumeUrl(candidate.getResumeUrl())
                .status(candidate.getStatus())
                .files(mapFilesToDTOs(candidate.getFiles()))
                .evaluations(mapEvaluationsToDTOs(candidate.getEvaluations()))
                .createdBy(candidate.getCreatedBy())
                .createdAt(candidate.getCreatedAt())
                .updatedBy(candidate.getUpdatedBy())
                .updatedAt(candidate.getUpdatedAt())
                .build();
    }

    /**
     * Convert Candidate DTO to entity
     * @param dto the candidate DTO
     * @return the candidate entity
     */
    public Candidate toEntity(CandidateDTO dto) {
        if (dto == null) {
            return null;
        }

        Candidate candidate = Candidate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .englishName(dto.getEnglishName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .partnerCompany(dto.getPartnerCompany())
                .yearsOfExperience(dto.getYearsOfExperience())
                .technicalGrade(dto.getTechnicalGrade())
                .skills(dto.getSkills())
                .experience(dto.getExperience())
                .availabilityDate(dto.getAvailabilityDate())
                .currentLocation(dto.getCurrentLocation())
                .preferredLocation(dto.getPreferredLocation())
                .noticePeriod(dto.getNoticePeriod())
                .expectedSalary(dto.getExpectedSalary())
                .resumeUrl(dto.getResumeUrl())
                .status(dto.getStatus())
                .createdBy(dto.getCreatedBy())
                .createdAt(dto.getCreatedAt())
                .updatedBy(dto.getUpdatedBy())
                .updatedAt(dto.getUpdatedAt())
                .build();

        // Set bidirectional relationships
        if (dto.getFiles() != null) {
            candidate.setFiles(mapFilesToEntities(dto.getFiles(), candidate));
        }
        if (dto.getEvaluations() != null) {
            candidate.setEvaluations(mapEvaluationsToEntities(dto.getEvaluations(), candidate));
        }

        return candidate;
    }

    /**
     * Update an existing Candidate entity with DTO data
     * @param candidate the existing candidate entity
     * @param dto the candidate DTO with updated data
     */
    public void updateEntityFromDTO(Candidate candidate, CandidateDTO dto) {
        if (candidate == null || dto == null) {
            return;
        }

        candidate.setName(dto.getName());
        candidate.setEnglishName(dto.getEnglishName());
        candidate.setEmail(dto.getEmail());
        candidate.setPhoneNumber(dto.getPhoneNumber());
        candidate.setPartnerCompany(dto.getPartnerCompany());
        candidate.setYearsOfExperience(dto.getYearsOfExperience());
        candidate.setTechnicalGrade(dto.getTechnicalGrade());
        candidate.setSkills(dto.getSkills());
        candidate.setExperience(dto.getExperience());
        candidate.setAvailabilityDate(dto.getAvailabilityDate());
        candidate.setCurrentLocation(dto.getCurrentLocation());
        candidate.setPreferredLocation(dto.getPreferredLocation());
        candidate.setNoticePeriod(dto.getNoticePeriod());
        candidate.setExpectedSalary(dto.getExpectedSalary());
        candidate.setResumeUrl(dto.getResumeUrl());
        candidate.setStatus(dto.getStatus());
        candidate.setUpdatedBy(dto.getUpdatedBy());
        candidate.setUpdatedAt(dto.getUpdatedAt());
    }

    /**
     * Convert a list of Candidate entities to DTOs
     * @param candidates the list of candidate entities
     * @return the list of candidate DTOs
     */
    public List<CandidateDTO> toDTOList(List<Candidate> candidates) {
        if (candidates == null) {
            return new ArrayList<>();
        }
        return candidates.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert CandidateFile entity to DTO
     */
    private CandidateFileDTO mapFileToDTO(CandidateFile file) {
        if (file == null) {
            return null;
        }
        return CandidateFileDTO.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .filePath(file.getFilePath())
                .fileSize(file.getFileSize())
                .uploadDate(file.getUploadDate())
                .uploadedBy(file.getUploadedBy())
                .documentType(file.getDocumentType())
                .description(file.getDescription())
                .build();
    }

    /**
     * Convert CandidateFile DTO to entity
     */
    private CandidateFile mapFileToEntity(CandidateFileDTO dto, Candidate candidate) {
        if (dto == null) {
            return null;
        }
        CandidateFile file = CandidateFile.builder()
                .id(dto.getId())
                .candidate(candidate)
                .fileName(dto.getFileName())
                .fileType(dto.getFileType())
                .filePath(dto.getFilePath())
                .fileSize(dto.getFileSize())
                .uploadDate(dto.getUploadDate())
                .uploadedBy(dto.getUploadedBy())
                .documentType(dto.getDocumentType())
                .description(dto.getDescription())
                .build();
        return file;
    }

    /**
     * Convert list of CandidateFile entities to DTOs
     */
    private List<CandidateFileDTO> mapFilesToDTOs(List<CandidateFile> files) {
        if (files == null) {
            return new ArrayList<>();
        }
        return files.stream()
                .map(this::mapFileToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert list of CandidateFile DTOs to entities
     */
    private List<CandidateFile> mapFilesToEntities(List<CandidateFileDTO> dtos, Candidate candidate) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        return dtos.stream()
                .map(dto -> mapFileToEntity(dto, candidate))
                .collect(Collectors.toList());
    }

    /**
     * Convert list of Evaluation entities to DTOs
     */
    private List<EvaluationDTO> mapEvaluationsToDTOs(List<Evaluation> evaluations) {
        if (evaluations == null) {
            return new ArrayList<>();
        }
        return evaluations.stream()
                .map(evaluationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert list of Evaluation DTOs to entities
     */
    private List<Evaluation> mapEvaluationsToEntities(List<EvaluationDTO> dtos, Candidate candidate) {
        if (dtos == null) {
            return new ArrayList<>();
        }
        List<Evaluation> evaluations = dtos.stream()
                .map(evaluationMapper::toEntity)
                .collect(Collectors.toList());
        
        // Set bidirectional relationship
        evaluations.forEach(evaluation -> evaluation.setCandidate(candidate));
        return evaluations;
    }
} 