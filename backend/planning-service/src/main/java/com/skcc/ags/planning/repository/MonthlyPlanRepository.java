package com.skcc.ags.planning.repository;

import com.skcc.ags.planning.domain.MonthlyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyPlanRepository extends JpaRepository<MonthlyPlan, Long> {
    @Query("SELECT mp FROM MonthlyPlan mp LEFT JOIN FETCH mp.gradePlans WHERE mp.id = :id")
    Optional<MonthlyPlan> findByIdWithGradePlans(@Param("id") Long id);

    List<MonthlyPlan> findByProjectId(Long projectId);

    @Query("SELECT mp FROM MonthlyPlan mp WHERE mp.projectId = :projectId AND mp.planYear = :year AND mp.planMonth = :month")
    Optional<MonthlyPlan> findByProjectIdAndYearAndMonth(
        @Param("projectId") Long projectId,
        @Param("year") Integer year,
        @Param("month") Integer month
    );

    @Query("SELECT DISTINCT mp.planYear FROM MonthlyPlan mp ORDER BY mp.planYear DESC")
    List<Integer> findDistinctYears();

    @Query("SELECT mp FROM MonthlyPlan mp LEFT JOIN FETCH mp.gradePlans " +
           "WHERE mp.projectId = :projectId AND mp.planYear = :year")
    List<MonthlyPlan> findByProjectIdAndYearWithGradePlans(
        @Param("projectId") Long projectId,
        @Param("year") Integer year
    );
} 