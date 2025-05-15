package com.skcc.ags.talent.mapper;

import com.skcc.ags.talent.domain.Proposal;
import com.skcc.ags.talent.domain.ProposalFeedback;
import com.skcc.ags.talent.dto.ProposalDTO;
import com.skcc.ags.talent.dto.ProposalFeedbackDTO;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Proposal entities and DTOs.
 */
@Mapper(componentModel = "spring", 
        uses = {CandidateMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProposalMapper {

    /**
     * Convert a Proposal entity to a ProposalDTO.
     *
     * @param proposal the entity to convert
     * @return the converted DTO
     */
    @Mapping(target = "candidateId", source = "candidate.id")
    @Mapping(target = "feedbackList", ignore = true)
    ProposalDTO toDTO(Proposal proposal);

    /**
     * Convert a ProposalDTO to a Proposal entity.
     *
     * @param proposalDTO the DTO to convert
     * @return the converted entity
     */
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "feedbackList", ignore = true)
    Proposal toEntity(ProposalDTO proposalDTO);

    /**
     * Update a Proposal entity with data from a ProposalDTO.
     *
     * @param proposalDTO the source DTO
     * @param proposal the entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "feedbackList", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void updateEntityFromDTO(ProposalDTO proposalDTO, @MappingTarget Proposal proposal);

    /**
     * Convert a list of Proposal entities to DTOs.
     *
     * @param proposals the list of entities
     * @return the list of DTOs
     */
    default List<ProposalDTO> toDTOList(List<Proposal> proposals) {
        return proposals.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert a Page of Proposal entities to DTOs.
     *
     * @param proposalPage the page of entities
     * @return the page of DTOs
     */
    default Page<ProposalDTO> toDTOPage(Page<Proposal> proposalPage) {
        return proposalPage.map(this::toDTO);
    }

    /**
     * Convert a list of ProposalFeedback entities to DTOs.
     *
     * @param feedbackList the list of feedback entities
     * @return the list of feedback DTOs
     */
    default List<ProposalFeedbackDTO> toFeedbackDTOList(List<ProposalFeedback> feedbackList) {
        if (feedbackList == null) {
            return null;
        }
        return feedbackList.stream()
                .map(this::toFeedbackDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert a ProposalFeedback entity to DTO.
     *
     * @param feedback the feedback entity
     * @return the feedback DTO
     */
    @Mapping(target = "proposalId", source = "proposal.id")
    ProposalFeedbackDTO toFeedbackDTO(ProposalFeedback feedback);

    /**
     * Convert a ProposalFeedbackDTO to entity.
     *
     * @param feedbackDTO the feedback DTO
     * @return the feedback entity
     */
    @Mapping(target = "proposal", ignore = true)
    ProposalFeedback toFeedbackEntity(ProposalFeedbackDTO feedbackDTO);

    /**
     * Update a ProposalFeedback entity with data from a DTO.
     *
     * @param feedbackDTO the source DTO
     * @param feedback the entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proposal", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void updateFeedbackFromDTO(ProposalFeedbackDTO feedbackDTO, @MappingTarget ProposalFeedback feedback);
} 