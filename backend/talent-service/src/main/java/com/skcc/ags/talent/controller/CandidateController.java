package com.ags.talent.controller;

import com.ags.talent.dto.CandidateDTO;
import com.ags.talent.dto.CandidateFilterDTO;
import com.ags.talent.service.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER')")
    public ResponseEntity<Page<CandidateDTO>> getCandidates(
            CandidateFilterDTO filters,
            Pageable pageable) {
        return ResponseEntity.ok(candidateService.getCandidates(filters, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER')")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<CandidateDTO> createCandidate(
            @Valid @RequestBody CandidateDTO candidateDTO) {
        return ResponseEntity.ok(candidateService.createCandidate(candidateDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<CandidateDTO> updateCandidate(
            @PathVariable Long id,
            @Valid @RequestBody CandidateDTO candidateDTO) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, candidateDTO));
    }

    @PostMapping(value = "/{id}/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<CandidateDTO> uploadFiles(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) {
        return ResponseEntity.ok(candidateService.uploadFiles(id, files));
    }

    @GetMapping("/files/{fileId}")
    @PreAuthorize("hasAnyRole('PM', 'PARTNER_MANAGER')")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        return candidateService.downloadFile(fileId);
    }

    @DeleteMapping("/files/{fileId}")
    @PreAuthorize("hasRole('PARTNER_MANAGER')")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        candidateService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }
} 