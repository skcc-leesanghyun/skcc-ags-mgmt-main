package com.skcc.ags.knowledge.repository;

import com.skcc.ags.knowledge.domain.Answer;
import com.skcc.ags.knowledge.domain.Vote;
import com.skcc.ags.knowledge.domain.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 투표 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리 인터페이스
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * 특정 답변에 대한 사용자의 투표를 찾습니다.
     *
     * @param answer   답변 엔티티
     * @param username 사용자명
     * @param type     투표 유형
     * @return 투표 Optional
     */
    Optional<Vote> findByAnswerAndUsernameAndType(Answer answer, String username, VoteType type);

    /**
     * 특정 답변에 대한 추천/비추천 투표 수를 계산합니다.
     *
     * @param answer 답변 엔티티
     * @param type   투표 유형
     * @return 투표 수
     */
    long countByAnswerAndType(Answer answer, VoteType type);

    /**
     * 특정 답변에 대한 모든 투표를 조회합니다.
     *
     * @param answer 답변 엔티티
     * @return 투표 목록
     */
    List<Vote> findByAnswer(Answer answer);

    /**
     * 특정 사용자가 한 모든 투표를 조회합니다.
     *
     * @param username 사용자명
     * @return 투표 목록
     */
    List<Vote> findByUsername(String username);

    /**
     * 특정 답변에 대한 사용자의 모든 투표를 삭제합니다.
     *
     * @param answer   답변 엔티티
     * @param username 사용자명
     * @return 삭제된 투표 수
     */
    long deleteByAnswerAndUsername(Answer answer, String username);

    /**
     * 특정 답변의 추천/비추천 투표 통계를 조회합니다.
     *
     * @param answerId 답변 ID
     * @return 투표 유형별 개수
     */
    @Query("SELECT v.type as voteType, COUNT(v) as count " +
           "FROM Vote v " +
           "WHERE v.answer.id = :answerId " +
           "GROUP BY v.type")
    List<VoteStats> getVoteStatsByAnswerId(@Param("answerId") Long answerId);

    /**
     * 투표 통계 정보를 담는 인터페이스
     */
    interface VoteStats {
        VoteType getVoteType();
        long getCount();
    }

    /**
     * 특정 답변에 대한 특정 사용자의 특정 타입의 투표를 조회
     */
    Optional<Vote> findByAnswerIdAndUsernameAndType(Long answerId, String username, VoteType type);

    /**
     * 특정 답변에 대한 특정 사용자의 특정 타입의 투표 존재 여부를 확인
     */
    boolean existsByAnswerIdAndUsernameAndType(Long answerId, String username, VoteType type);

    /**
     * 특정 답변에 대한 모든 투표를 삭제
     */
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.answer.id = :answerId")
    void deleteByAnswerId(@Param("answerId") Long answerId);

    /**
     * 특정 답변에 대한 추천 수를 조회
     */
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.answer.id = :answerId AND v.type = 'UPVOTE'")
    long countUpvotesByAnswerId(@Param("answerId") Long answerId);

    /**
     * 특정 답변에 대한 비추천 수를 조회
     */
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.answer.id = :answerId AND v.type = 'DOWNVOTE'")
    long countDownvotesByAnswerId(@Param("answerId") Long answerId);

    /**
     * 특정 사용자가 특정 기간 동안 행한 투표 목록을 조회
     */
    @Query("SELECT v FROM Vote v WHERE v.username = :username AND v.createdAt BETWEEN :startDate AND :endDate")
    List<Vote> findByUsernameAndDateRange(
            @Param("username") String username,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 특정 사용자의 추천 투표 수를 조회
     */
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.username = :username AND v.type = 'UPVOTE'")
    long countUpvotesByUsername(@Param("username") String username);

    /**
     * 특정 사용자의 비추천 투표 수를 조회
     */
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.username = :username AND v.type = 'DOWNVOTE'")
    long countDownvotesByUsername(@Param("username") String username);

    /**
     * 특정 답변들에 대한 모든 투표를 삭제
     */
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.answer.id IN :answerIds")
    void deleteByAnswerIds(@Param("answerIds") List<Long> answerIds);

    /**
     * 특정 답변에 대한 모든 투표 목록을 조회
     */
    @Query("SELECT v FROM Vote v WHERE v.answer.id = :answerId ORDER BY v.createdAt DESC")
    List<Vote> findByAnswerIdOrderByCreatedAtDesc(@Param("answerId") Long answerId);

    /**
     * 특정 기간 동안의 투표 통계를 조회
     */
    @Query("SELECT v.type, COUNT(v) FROM Vote v WHERE v.createdAt BETWEEN :startDate AND :endDate GROUP BY v.type")
    List<Object[]> getVoteStatsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * 특정 사용자가 투표한 답변 ID 목록을 조회
     */
    @Query("SELECT v.answer.id FROM Vote v WHERE v.username = :username AND v.type = :type")
    List<Long> findAnswerIdsByUsernameAndType(
            @Param("username") String username,
            @Param("type") VoteType type
    );

    /**
     * 특정 답변에 대한 투표 요약 정보를 조회
     */
    @Query("SELECT v.type, COUNT(v) FROM Vote v WHERE v.answer.id = :answerId GROUP BY v.type")
    List<Object[]> getVoteSummaryByAnswerId(@Param("answerId") Long answerId);
} 