package com.skcc.ags.knowledge.mapper;

import com.skcc.ags.knowledge.domain.Answer;
import com.skcc.ags.knowledge.domain.Vote;
import com.skcc.ags.knowledge.dto.VoteDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Vote 엔티티와 VoteDTO 간의 변환을 처리하는 매퍼 클래스
 */
@Component
public class VoteMapper {

    /**
     * Vote 엔티티를 VoteDTO로 변환합니다.
     *
     * @param vote Vote 엔티티
     * @return VoteDTO 객체, null인 경우 null 반환
     */
    public VoteDTO toDTO(Vote vote) {
        if (vote == null) {
            return null;
        }

        return VoteDTO.builder()
                .id(vote.getId())
                .answerId(vote.getAnswer().getId())
                .username(vote.getUsername())
                .type(vote.getType())
                .createdAt(vote.getCreatedAt())
                .build();
    }

    /**
     * VoteDTO를 Vote 엔티티로 변환합니다.
     *
     * @param voteDTO VoteDTO 객체
     * @param answer  연관된 Answer 엔티티
     * @return Vote 엔티티, null인 경우 null 반환
     */
    public Vote toEntity(VoteDTO voteDTO, Answer answer) {
        if (voteDTO == null) {
            return null;
        }

        return Vote.builder()
                .id(voteDTO.getId())
                .answer(answer)
                .username(voteDTO.getUsername())
                .type(voteDTO.getType())
                .createdAt(voteDTO.getCreatedAt())
                .build();
    }

    /**
     * Vote 엔티티 목록을 VoteDTO 목록으로 변환합니다.
     *
     * @param votes Vote 엔티티 목록
     * @return VoteDTO 목록
     */
    public List<VoteDTO> toDTOList(List<Vote> votes) {
        if (votes == null) {
            return null;
        }

        return votes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * VoteDTO 목록을 Vote 엔티티 목록으로 변환합니다.
     *
     * @param voteDTOs VoteDTO 목록
     * @param answer   연관된 Answer 엔티티
     * @return Vote 엔티티 목록
     */
    public List<Vote> toEntityList(List<VoteDTO> voteDTOs, Answer answer) {
        if (voteDTOs == null) {
            return null;
        }

        return voteDTOs.stream()
                .map(dto -> toEntity(dto, answer))
                .collect(Collectors.toList());
    }
} 