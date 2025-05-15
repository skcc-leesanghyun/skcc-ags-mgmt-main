package com.skcc.ags.knowledge.service;

import com.skcc.ags.knowledge.domain.VoteType;
import com.skcc.ags.knowledge.dto.VoteDTO;
import com.skcc.ags.knowledge.repository.VoteRepository.VoteStats;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 투표 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
public interface VoteService {

    /**
     * 답변에 대한 투표를 생성합니다.
     *
     * @param answerId 답변 ID
     * @param type     투표 유형
     * @param username 사용자명
     * @return 생성된 투표 정보
     */
    VoteDTO createVote(Long answerId, VoteType type, String username);

    /**
     * 답변에 대한 투표를 삭제합니다.
     *
     * @param answerId 답변 ID
     * @param type     투표 유형
     * @param username 사용자명
     */
    void deleteVote(Long answerId, VoteType type, String username);

    /**
     * 답변에 대한 투표 통계를 조회합니다.
     *
     * @param answerId 답변 ID
     * @return 투표 통계 정보
     */
    List<VoteStats> getVoteStats(Long answerId);

    /**
     * 특정 답변에 대한 사용자의 투표 여부를 확인합니다.
     *
     * @param answerId 답변 ID
     * @param username 사용자명
     * @param type     투표 유형
     * @return 투표 여부
     */
    boolean hasUserVoted(Long answerId, String username, VoteType type);

    /**
     * 특정 기간 동안의 투표 통계를 조회합니다.
     *
     * @param startDate 시작 일시
     * @param endDate   종료 일시
     * @return 기간별 투표 통계
     */
    Map<VoteType, Long> getVoteStatsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 사용자의 투표 이력을 조회합니다.
     *
     * @param username 사용자명
     * @return 사용자의 투표 목록
     */
    List<VoteDTO> getUserVoteHistory(String username);

    /**
     * 특정 답변의 모든 투표를 조회합니다.
     *
     * @param answerId 답변 ID
     * @return 답변에 대한 투표 목록
     */
    List<VoteDTO> getVotesByAnswer(Long answerId);

    /**
     * 특정 사용자의 투표 통계를 조회합니다.
     *
     * @param username 사용자명
     * @return 사용자별 투표 통계 (추천/비추천 수)
     */
    Map<VoteType, Long> getUserVoteStats(String username);

    /**
     * 특정 답변에 대한 투표 요약 정보를 조회합니다.
     *
     * @param answerId 답변 ID
     * @return 투표 요약 정보 (총 추천 수, 총 비추천 수)
     */
    Map<String, Long> getVoteSummary(Long answerId);

    /**
     * 특정 사용자가 투표한 답변 ID 목록을 조회합니다.
     *
     * @param username 사용자명
     * @param type     투표 유형
     * @return 답변 ID 목록
     */
    List<Long> getVotedAnswerIds(String username, VoteType type);
} 