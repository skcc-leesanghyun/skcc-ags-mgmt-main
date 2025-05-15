package com.ags.talent.repository;

import com.ags.talent.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT e FROM Evaluation e JOIN e.candidate c " +
            "WHERE (:candidateName IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :candidateName, '%'))) " +
            "AND (:score IS NULL OR e.score >= :score) " +
            "AND (:evaluator IS NULL OR LOWER(e.evaluator) = LOWER(:evaluator))")
    List<Evaluation> findByFilters(
            @Param("candidateName") String candidateName,
            @Param("score") Integer score,
            @Param("evaluator") String evaluator);

    @Query("SELECT DISTINCT e.evaluator FROM Evaluation e ORDER BY e.evaluator")
    List<String> findDistinctEvaluators();

    List<Evaluation> findByCandidateId(Long candidateId);
    List<Evaluation> findByCandidateIdAndStatus(Long candidateId, String status);
    boolean existsByCandidateIdAndEvaluatorId(Long candidateId, Long evaluatorId);
} 