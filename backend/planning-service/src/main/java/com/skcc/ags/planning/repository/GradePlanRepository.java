package com.skcc.ags.planning.repository;

import com.skcc.ags.planning.domain.GradePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradePlanRepository extends JpaRepository<GradePlan, Long> {
    List<GradePlan> findByMonthlyPlanId(Long monthlyPlanId);

    @Query("SELECT DISTINCT gp.grade FROM GradePlan gp ORDER BY gp.grade")
    List<String> findDistinctGrades();

    @Query("SELECT gp FROM GradePlan gp " +
           "JOIN FETCH gp.monthlyPlan mp " +
           "WHERE mp.projectId = :projectId AND mp.planYear = :year AND mp.planMonth = :month")
    List<GradePlan> findByProjectIdAndYearAndMonth(
        @Param("projectId") Long projectId,
        @Param("year") Integer year,
        @Param("month") Integer month
    );
} 