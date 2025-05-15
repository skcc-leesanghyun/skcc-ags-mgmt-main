package com.ags.talent.service.impl;

import com.ags.talent.dto.CandidateDTO;
import com.ags.talent.dto.CandidateFilter;
import com.ags.talent.entity.Candidate;
import com.ags.talent.entity.CandidateFile;
import com.ags.talent.exception.ResourceNotFoundException;
import com.ags.talent.mapper.CandidateMapper;
import com.ags.talent.repository.CandidateFileRepository;
import com.ags.talent.repository.CandidateRepository;
import com.ags.talent.service.CandidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateFileRepository candidateFileRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public Page<CandidateDTO> getCandidates(CandidateFilter filter, Pageable pageable) {
        Page<Candidate> candidates = candidateRepository.findByFilter(filter, pageable);
        return candidates.map(candidateMapper::toDTO);
    }

    @Override
    public CandidateDTO getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));
        return candidateMapper.toDTO(candidate);
    }

    @Override
    @Transactional
    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = candidateMapper.toEntity(candidateDTO);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.toDTO(candidate);
    }

    @Override
    @Transactional
    public CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO) {
        Candidate existingCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));
        
        candidateMapper.updateEntityFromDTO(candidateDTO, existingCandidate);
        existingCandidate = candidateRepository.save(existingCandidate);
        return candidateMapper.toDTO(existingCandidate);
    }

    @Override
    @Transactional
    public void uploadFiles(Long candidateId, List<MultipartFile> files) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + candidateId));

        for (MultipartFile file : files) {
            try {
                CandidateFile candidateFile = CandidateFile.builder()
                        .candidate(candidate)
                        .fileName(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .fileData(file.getBytes())
                        .build();
                candidateFileRepository.save(candidateFile);
            } catch (IOException e) {
                log.error("Error uploading file: {}", file.getOriginalFilename(), e);
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename());
            }
        }
    }

    @Override
    public byte[] downloadFile(Long fileId) {
        CandidateFile file = candidateFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + fileId));
        return file.getFileData();
    }

    @Override
    @Transactional
    public void deleteFile(Long fileId) {
        if (!candidateFileRepository.existsById(fileId)) {
            throw new ResourceNotFoundException("File not found with id: " + fileId);
        }
        candidateFileRepository.deleteById(fileId);
    }
} 