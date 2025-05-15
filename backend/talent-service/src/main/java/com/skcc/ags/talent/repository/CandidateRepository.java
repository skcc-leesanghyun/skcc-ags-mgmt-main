package com.ags.talent.repository;

import com.ags.talent.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT DISTINCT c FROM Candidate c " +
            "LEFT JOIN c.evaluations e " +
            "WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:skills IS NULL OR c.skills IN :skills) " +
            "AND (:minYearsOfExperience IS NULL OR c.yearsOfExperience >= :minYearsOfExperience) " +
            "AND (:maxYearsOfExperience IS NULL OR c.yearsOfExperience <= :maxYearsOfExperience) " +
            "AND (:minScore IS NULL OR (SELECT AVG(e2.score) FROM Evaluation e2 WHERE e2.candidate = c) >= :minScore)")
    Page<Candidate> findByFilters(
            @Param("name") String name,
            @Param("skills") Set<String> skills,
            @Param("minYearsOfExperience") Integer minYearsOfExperience,
            @Param("maxYearsOfExperience") Integer maxYearsOfExperience,
            @Param("minScore") Double minScore,
            Pageable pageable);
} 