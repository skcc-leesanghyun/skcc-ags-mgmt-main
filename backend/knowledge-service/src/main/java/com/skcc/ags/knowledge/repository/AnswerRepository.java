package com.skcc.ags.knowledge.repository;

import com.skcc.ags.knowledge.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 답변 엔티티에 대한 데이터 액세스를 제공하는 리포지토리 인터페이스
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    /**
     * 특정 질문에 대한 답변들을 생성일시 기준 내림차순으로 조회
     */
    List<Answer> findByQuestionIdOrderByCreatedAtDesc(Long questionId);

    /**
     * 특정 사용자가 작성한 답변들을 생성일시 기준 내림차순으로 조회
     */
    List<Answer> findByCreatedByOrderByCreatedAtDesc(String username);

    /**
     * 특정 사용자가 특정 질문에 이미 답변을 작성했는지 확인
     */
    boolean existsByQuestionIdAndCreatedBy(Long questionId, String username);

    /**
     * 특정 질문의 모든 답변의 채택 상태를 해제
     */
    @Modifying
    @Query("UPDATE Answer a SET a.accepted = false WHERE a.question.id = :questionId")
    void resetAcceptedStatusForQuestion(@Param("questionId") Long questionId);

    /**
     * 특정 질문의 채택된 답변을 조회
     */
    Optional<Answer> findByQuestionIdAndAcceptedTrue(Long questionId);

    /**
     * 모든 답변을 생성일시 기준 내림차순으로 페이징하여 조회
     */
    Page<Answer> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * 추천수가 가장 높은 상위 N개의 답변을 조회
     */
    @Query("SELECT a FROM Answer a ORDER BY a.upvoteCount DESC")
    List<Answer> findTopByUpvoteCount(int limit);

    /**
     * 특정 사용자의 채택된 답변 수를 조회
     */
    long countByCreatedByAndAcceptedTrue(String username);

    /**
     * 특정 질문의 답변 수를 조회
     */
    long countByQuestionId(Long questionId);

    /**
     * 특정 기간 내의 답변들을 조회
     */
    @Query("SELECT a FROM Answer a WHERE a.createdAt BETWEEN :startDate AND :endDate")
    List<Answer> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * 특정 사용자의 답변들 중 채택된 답변들을 조회
     */
    List<Answer> findByCreatedByAndAcceptedTrue(String username);

    /**
     * 특정 기간 내의 답변 수를 조회
     */
    @Query("SELECT COUNT(a) FROM Answer a WHERE a.createdAt BETWEEN :startDate AND :endDate")
    long countByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * 추천수와 비추천수의 차이가 가장 큰 상위 N개의 답변을 조회
     */
    @Query("SELECT a FROM Answer a ORDER BY (a.upvoteCount - a.downvoteCount) DESC")
    List<Answer> findTopByVoteDifference(Pageable pageable);

    /**
     * 특정 사용자의 답변들 중 추천수가 특정 값 이상인 답변들을 조회
     */
    @Query("SELECT a FROM Answer a WHERE a.createdBy = :username AND a.upvoteCount >= :minUpvotes")
    List<Answer> findByCreatedByAndMinUpvotes(@Param("username") String username, @Param("minUpvotes") int minUpvotes);

    /**
     * 특정 질문의 답변들을 추천수 기준 내림차순으로 조회
     */
    List<Answer> findByQuestionIdOrderByUpvoteCountDesc(Long questionId);
} 