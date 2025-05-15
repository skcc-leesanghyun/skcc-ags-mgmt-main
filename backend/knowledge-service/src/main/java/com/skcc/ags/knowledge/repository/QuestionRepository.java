package com.skcc.ags.knowledge.repository;

import com.skcc.ags.knowledge.domain.Question;
import com.skcc.ags.knowledge.domain.QuestionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    // 카테고리별 질문 조회 (페이징)
    Page<Question> findByCategory(QuestionCategory category, Pageable pageable);
    
    // 제목 또는 내용으로 검색 (페이징)
    @Query("SELECT q FROM Question q WHERE " +
           "LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Question> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    // 특정 사용자가 작성한 질문 조회
    List<Question> findByCreatedByOrderByCreatedAtDesc(String username);
    
    // 조회수 상위 N개 질문 조회
    @Query("SELECT q FROM Question q ORDER BY q.viewCount DESC")
    List<Question> findTopByViewCount(Pageable pageable);
    
    // 카테고리별 질문 수 조회
    long countByCategory(QuestionCategory category);
    
    // 특정 기간 내 작성된 질문 조회
    @Query("SELECT q FROM Question q WHERE q.createdAt >= :startDate AND q.createdAt <= :endDate")
    List<Question> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 답변이 없는 질문 조회
    @Query("SELECT q FROM Question q WHERE SIZE(q.answers) = 0")
    List<Question> findQuestionsWithNoAnswers();
    
    // 채택된 답변이 있는 질문 조회
    @Query("SELECT DISTINCT q FROM Question q JOIN q.answers a WHERE a.accepted = true")
    List<Question> findQuestionsWithAcceptedAnswers();
} 