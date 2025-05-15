package com.skcc.ags.talent.service.impl;

import com.skcc.ags.talent.domain.Proposal;
import com.skcc.ags.talent.domain.ProposalFeedback;
import com.skcc.ags.talent.domain.ProposalStatus;
import com.skcc.ags.talent.dto.ProposalDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import com.skcc.ags.talent.repository.ProposalRepository;
import com.skcc.ags.talent.repository.ProposalFeedbackRepository;
import com.skcc.ags.talent.service.ProposalService;
import com.skcc.ags.talent.service.ProposalStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of ProposalService interface
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final ProposalFeedbackRepository feedbackRepository;

    @Override
    public ProposalDTO createProposal(ProposalDTO proposalDTO) {
        log.info("Creating new proposal");
        Proposal proposal = convertToEntity(proposalDTO);
        proposal.setStatus(ProposalStatus.DRAFT);
        proposal.setCreatedAt(LocalDateTime.now());
        proposal.setActive(true);
        
        Proposal savedProposal = proposalRepository.save(proposal);
        return convertToDTO(savedProposal);
    }

    @Override
    public ProposalDTO updateProposal(Long id, ProposalDTO proposalDTO) {
        log.info("Updating proposal with ID: {}", id);
        Proposal existingProposal = proposalRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Proposal not found with ID: " + id));

        if (!existingProposal.getStatus().allowsEditing()) {
            throw new IllegalStateException("Proposal cannot be edited in current status: " + existingProposal.getStatus());
        }

        updateProposalFields(existingProposal, proposalDTO);
        Proposal updatedProposal = proposalRepository.save(existingProposal);
        return convertToDTO(updatedProposal);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProposalDTO> getProposalById(Long id) {
        log.info("Fetching proposal with ID: {}", id);
        return proposalRepository.findByIdWithFeedback(id)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getProposalsByProject(Long projectId, Pageable pageable) {
        log.info("Fetching proposals for project ID: {}", projectId);
        return proposalRepository.findByProjectIdAndActiveTrue(projectId, pageable)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getProposalsByCandidate(Long candidateId, Pageable pageable) {
        log.info("Fetching proposals for candidate ID: {}", candidateId);
        return proposalRepository.findByCandidateId(candidateId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> new PageImpl<>(list, pageable, list.size())
            ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getProposalsByPartnerCompany(Long partnerCompanyId, Pageable pageable) {
        log.info("Fetching proposals for partner company ID: {}", partnerCompanyId);
        return proposalRepository.findByPartnerCompanyId(partnerCompanyId)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> new PageImpl<>(list, pageable, list.size())
            ));
    }

    @Override
    public ProposalDTO submitProposal(Long id) {
        log.info("Submitting proposal with ID: {}", id);
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Proposal not found with ID: " + id));

        if (proposal.getStatus() != ProposalStatus.DRAFT) {
            throw new IllegalStateException("Only draft proposals can be submitted");
        }

        proposal.setStatus(ProposalStatus.SUBMITTED);
        proposal.setSubmissionDate(LocalDateTime.now());
        Proposal submittedProposal = proposalRepository.save(proposal);
        return convertToDTO(submittedProposal);
    }

    @Override
    public ProposalFeedbackDTO addFeedback(Long proposalId, ProposalFeedbackDTO feedbackDTO) {
        log.info("Adding feedback to proposal with ID: {}", proposalId);
        Proposal proposal = proposalRepository.findById(proposalId)
            .orElseThrow(() -> new EntityNotFoundException("Proposal not found with ID: " + proposalId));

        if (!proposal.getStatus().allowsFeedback()) {
            throw new IllegalStateException("Feedback cannot be added in current status: " + proposal.getStatus());
        }

        ProposalFeedback feedback = convertToFeedbackEntity(feedbackDTO);
        feedback.setProposal(proposal);
        feedback.setCreatedAt(LocalDateTime.now());
        
        ProposalFeedback savedFeedback = feedbackRepository.save(feedback);
        recalculateScores(proposalId);
        
        return convertToFeedbackDTO(savedFeedback);
    }

    @Override
    public ProposalDTO updateStatus(Long id, ProposalStatus status, String comment) {
        log.info("Updating status of proposal with ID: {} to {}", id, status);
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Proposal not found with ID: " + id));

        validateStatusTransition(proposal.getStatus(), status);
        proposal.setStatus(status);
        proposal.setModifiedAt(LocalDateTime.now());
        
        if (status.isFinal()) {
            proposal.setActive(false);
        }

        Proposal updatedProposal = proposalRepository.save(proposal);
        return convertToDTO(updatedProposal);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getProposalsRequiringReview(Pageable pageable) {
        log.info("Fetching proposals requiring review");
        return proposalRepository.findProposalsRequiringReview(pageable)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getProposalsByStatus(ProposalStatus status, Pageable pageable) {
        log.info("Fetching proposals with status: {}", status);
        return proposalRepository.findByStatus(status, pageable)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> searchProposals(String keyword, Pageable pageable) {
        log.info("Searching proposals with keyword: {}", keyword);
        return proposalRepository.searchByKeyword(keyword, pageable)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getExpiringProposals(LocalDateTime threshold, Pageable pageable) {
        log.info("Fetching proposals expiring before: {}", threshold);
        return proposalRepository.findByExpirationDateBeforeAndStatusNot(
            threshold, ProposalStatus.EXPIRED, pageable)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getHighScoringProposals(Double minimumScore, Pageable pageable) {
        log.info("Fetching proposals with minimum score: {}", minimumScore);
        return proposalRepository.findHighScoringProposals(minimumScore, pageable)
            .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProposalStatistics getProposalStatistics() {
        log.info("Generating proposal statistics");
        ProposalStatistics statistics = new ProposalStatistics();
        
        // Populate statistics
        statistics.setTotalProposals(proposalRepository.count());
        statistics.setActiveProposals(proposalRepository.countByStatus(ProposalStatus.ACTIVE));
        statistics.setPendingReview(proposalRepository.countByStatus(ProposalStatus.IN_REVIEW));
        statistics.setApprovedProposals(proposalRepository.countByStatus(ProposalStatus.APPROVED));
        statistics.setRejectedProposals(proposalRepository.countByStatus(ProposalStatus.REJECTED));
        
        // Additional statistics calculations can be added here
        
        return statistics;
    }

    @Override
    public void deleteProposal(Long id) {
        log.info("Deleting proposal with ID: {}", id);
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Proposal not found with ID: " + id));

        if (!proposal.getStatus().equals(ProposalStatus.DRAFT)) {
            throw new IllegalStateException("Only draft proposals can be deleted");
        }

        proposalRepository.delete(proposal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProposalFeedbackDTO> getProposalFeedback(Long proposalId) {
        log.info("Fetching feedback for proposal with ID: {}", proposalId);
        return feedbackRepository.findByProposalId(proposalId)
            .stream()
            .map(this::convertToFeedbackDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProposalFeedbackDTO> getFinalReviewFeedback(Long proposalId) {
        log.info("Fetching final review feedback for proposal with ID: {}", proposalId);
        return feedbackRepository.findByProposalIdAndFinalReviewTrue(proposalId)
            .map(this::convertToFeedbackDTO);
    }

    @Override
    public ProposalDTO recalculateScores(Long proposalId) {
        log.info("Recalculating scores for proposal with ID: {}", proposalId);
        Proposal proposal = proposalRepository.findById(proposalId)
            .orElseThrow(() -> new EntityNotFoundException("Proposal not found with ID: " + proposalId));

        List<ProposalFeedback> feedbackList = feedbackRepository.findByProposalId(proposalId);
        
        if (!feedbackList.isEmpty()) {
            double avgScore = feedbackList.stream()
                .mapToDouble(ProposalFeedback::getOverallScore)
                .average()
                .orElse(0.0);
            
            proposal.setAverageScore(avgScore);
            proposal.setFeedbackCount(feedbackList.size());
        }

        Proposal updatedProposal = proposalRepository.save(proposal);
        return convertToDTO(updatedProposal);
    }

    @Override
    public ProposalDTO extendExpirationDate(Long id, LocalDateTime newExpirationDate) {
        log.info("Extending expiration date for proposal with ID: {} to {}", id, newExpirationDate);
        Proposal proposal = proposalRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Proposal not found with ID: " + id));

        if (newExpirationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("New expiration date must be in the future");
        }

        proposal.setExpirationDate(newExpirationDate);
        proposal.setModifiedAt(LocalDateTime.now());
        
        Proposal updatedProposal = proposalRepository.save(proposal);
        return convertToDTO(updatedProposal);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProposalDTO> getProposalsByStatuses(List<ProposalStatus> statuses, Pageable pageable) {
        log.info("Fetching proposals with statuses: {}", statuses);
        return proposalRepository.findByStatusIn(statuses, pageable)
            .map(this::convertToDTO);
    }

    // Helper methods for entity-DTO conversion
    private Proposal convertToEntity(ProposalDTO dto) {
        Proposal proposal = new Proposal();
        // Set fields from DTO to entity
        proposal.setProjectId(dto.getProjectId());
        proposal.setCandidateId(dto.getCandidateId());
        proposal.setPartnerCompanyId(dto.getPartnerCompanyId());
        proposal.setPartnerCompanyName(dto.getPartnerCompanyName());
        proposal.setTitle(dto.getTitle());
        proposal.setDescription(dto.getDescription());
        proposal.setProposedStartDate(dto.getProposedStartDate());
        proposal.setExpectedDurationMonths(dto.getExpectedDurationMonths());
        proposal.setProposedRate(dto.getProposedRate());
        proposal.setRelevantSkills(dto.getRelevantSkills());
        proposal.setYearsOfExperience(dto.getYearsOfExperience());
        proposal.setPreviousExperience(dto.getPreviousExperience());
        proposal.setSpecialRequirements(dto.getSpecialRequirements());
        proposal.setAttachments(dto.getAttachments());
        return proposal;
    }

    private ProposalDTO convertToDTO(Proposal entity) {
        ProposalDTO dto = new ProposalDTO();
        // Set fields from entity to DTO
        dto.setId(entity.getId());
        dto.setProjectId(entity.getProjectId());
        dto.setCandidateId(entity.getCandidateId());
        dto.setPartnerCompanyId(entity.getPartnerCompanyId());
        dto.setPartnerCompanyName(entity.getPartnerCompanyName());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setProposedStartDate(entity.getProposedStartDate());
        dto.setExpectedDurationMonths(entity.getExpectedDurationMonths());
        dto.setProposedRate(entity.getProposedRate());
        dto.setRelevantSkills(entity.getRelevantSkills());
        dto.setYearsOfExperience(entity.getYearsOfExperience());
        dto.setPreviousExperience(entity.getPreviousExperience());
        dto.setSpecialRequirements(entity.getSpecialRequirements());
        dto.setAttachments(entity.getAttachments());
        dto.setSubmissionDate(entity.getSubmissionDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedAt(entity.getModifiedAt());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setReviewed(entity.isReviewed());
        dto.setActive(entity.isActive());
        dto.setAverageScore(entity.getAverageScore());
        dto.setFeedbackCount(entity.getFeedbackCount());
        return dto;
    }

    private ProposalFeedback convertToFeedbackEntity(ProposalFeedbackDTO dto) {
        ProposalFeedback feedback = new ProposalFeedback();
        // Set fields from DTO to entity
        feedback.setReviewerId(dto.getReviewerId());
        feedback.setReviewerName(dto.getReviewerName());
        feedback.setReviewerDepartment(dto.getReviewerDepartment());
        feedback.setComments(dto.getComments());
        feedback.setTechnicalScore(dto.getTechnicalScore());
        feedback.setExperienceScore(dto.getExperienceScore());
        feedback.setCostScore(dto.getCostScore());
        feedback.setOverallScore(dto.getOverallScore());
        feedback.setStrengths(dto.getStrengths());
        feedback.setAreasForImprovement(dto.getAreasForImprovement());
        feedback.setRequirements(dto.getRequirements());
        feedback.setRecommendation(dto.getRecommendation());
        feedback.setAdditionalNotes(dto.getAdditionalNotes());
        feedback.setFinalReview(dto.isFinalReview());
        return feedback;
    }

    private ProposalFeedbackDTO convertToFeedbackDTO(ProposalFeedback entity) {
        ProposalFeedbackDTO dto = new ProposalFeedbackDTO();
        // Set fields from entity to DTO
        dto.setId(entity.getId());
        dto.setProposalId(entity.getProposal().getId());
        dto.setReviewerId(entity.getReviewerId());
        dto.setReviewerName(entity.getReviewerName());
        dto.setReviewerDepartment(entity.getReviewerDepartment());
        dto.setComments(entity.getComments());
        dto.setTechnicalScore(entity.getTechnicalScore());
        dto.setExperienceScore(entity.getExperienceScore());
        dto.setCostScore(entity.getCostScore());
        dto.setOverallScore(entity.getOverallScore());
        dto.setStrengths(entity.getStrengths());
        dto.setAreasForImprovement(entity.getAreasForImprovement());
        dto.setRequirements(entity.getRequirements());
        dto.setRecommendation(entity.getRecommendation());
        dto.setAdditionalNotes(entity.getAdditionalNotes());
        dto.setFinalReview(entity.isFinalReview());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedAt(entity.getModifiedAt());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    private void updateProposalFields(Proposal existingProposal, ProposalDTO proposalDTO) {
        existingProposal.setTitle(proposalDTO.getTitle());
        existingProposal.setDescription(proposalDTO.getDescription());
        existingProposal.setProposedStartDate(proposalDTO.getProposedStartDate());
        existingProposal.setExpectedDurationMonths(proposalDTO.getExpectedDurationMonths());
        existingProposal.setProposedRate(proposalDTO.getProposedRate());
        existingProposal.setRelevantSkills(proposalDTO.getRelevantSkills());
        existingProposal.setYearsOfExperience(proposalDTO.getYearsOfExperience());
        existingProposal.setPreviousExperience(proposalDTO.getPreviousExperience());
        existingProposal.setSpecialRequirements(proposalDTO.getSpecialRequirements());
        existingProposal.setAttachments(proposalDTO.getAttachments());
        existingProposal.setModifiedAt(LocalDateTime.now());
    }

    private void validateStatusTransition(ProposalStatus currentStatus, ProposalStatus newStatus) {
        // Add validation logic for status transitions
        if (currentStatus.isFinal() && !newStatus.equals(ProposalStatus.EXPIRED)) {
            throw new IllegalStateException("Cannot change status of a proposal in final state: " + currentStatus);
        }
        
        // Add more specific validation rules as needed
    }
} 