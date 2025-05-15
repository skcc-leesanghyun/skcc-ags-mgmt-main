package com.ags.talent.service;

import com.ags.talent.dto.CandidateDTO;
import com.ags.talent.dto.CandidateFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
    Page<CandidateDTO> getCandidates(CandidateFilter filter, Pageable pageable);
    CandidateDTO getCandidateById(Long id);
    CandidateDTO createCandidate(CandidateDTO candidateDTO);
    CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO);
    void uploadFiles(Long candidateId, List<MultipartFile> files);
    byte[] downloadFile(Long fileId);
    void deleteFile(Long fileId);
} 