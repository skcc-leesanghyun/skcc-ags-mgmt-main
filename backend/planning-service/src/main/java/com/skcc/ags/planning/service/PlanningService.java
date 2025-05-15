package com.skcc.ags.planning.service;

import com.skcc.ags.planning.domain.GradePlan;
import com.skcc.ags.planning.domain.KpiData;
import com.skcc.ags.planning.domain.MonthlyPlan;
import com.skcc.ags.planning.dto.GradePlanDTO;
import com.skcc.ags.planning.dto.MonthlyPlanDTO;
import com.skcc.ags.planning.exception.DuplicateMonthlyPlanException;
import com.skcc.ags.planning.exception.InvalidMonthlyPlanException;
import com.skcc.ags.planning.exception.MonthlyPlanNotFoundException;
import com.skcc.ags.planning.repository.GradePlanRepository;
import com.skcc.ags.planning.repository.MonthlyPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PlanningService {

    private final MonthlyPlanRepository monthlyPlanRepository;
    private final GradePlanRepository gradePlanRepository;

    public PlanningService(MonthlyPlanRepository monthlyPlanRepository,
                          GradePlanRepository gradePlanRepository) {
        this.monthlyPlanRepository = monthlyPlanRepository;
        this.gradePlanRepository = gradePlanRepository;
    }

    @Transactional
    public MonthlyPlanDTO createMonthlyPlan(MonthlyPlanDTO monthlyPlanDTO) {
        validateNewMonthlyPlan(monthlyPlanDTO);

        MonthlyPlan monthlyPlan = monthlyPlanDTO.toDomain();
        monthlyPlan.setId(null); // 새로운 엔티티임을 보장

        // 등급별 계획 설정
        if (monthlyPlanDTO.getGradePlans() != null) {
            monthlyPlanDTO.getGradePlans().forEach(gradePlanDTO -> {
                GradePlan gradePlan = gradePlanDTO.toDomain();
                gradePlan.setId(null);
                monthlyPlan.addGradePlan(gradePlan);
            });
        }

        MonthlyPlan savedPlan = monthlyPlanRepository.save(monthlyPlan);
        return MonthlyPlanDTO.fromDomain(savedPlan);
    }

    @Transactional
    public MonthlyPlanDTO updateMonthlyPlan(Long id, MonthlyPlanDTO monthlyPlanDTO) {
        MonthlyPlan existingPlan = monthlyPlanRepository.findByIdWithGradePlans(id)
            .orElseThrow(() -> new MonthlyPlanNotFoundException(id));

        validateUpdateMonthlyPlan(id, monthlyPlanDTO);

        // 기존 등급별 계획 제거
        existingPlan.getGradePlans().clear();

        // 새로운 등급별 계획 추가
        monthlyPlanDTO.getGradePlans().forEach(gradePlanDTO -> {
            GradePlan gradePlan = gradePlanDTO.toDomain();
            gradePlan.setId(null);
            existingPlan.addGradePlan(gradePlan);
        });

        // 기본 정보 업데이트
        existingPlan.setProjectId(monthlyPlanDTO.getProjectId());
        existingPlan.setPlanYear(monthlyPlanDTO.getYear());
        existingPlan.setPlanMonth(monthlyPlanDTO.getMonth());

        MonthlyPlan updatedPlan = monthlyPlanRepository.save(existingPlan);
        return MonthlyPlanDTO.fromDomain(updatedPlan);
    }

    @Transactional
    public void deleteMonthlyPlan(Long id) {
        if (!monthlyPlanRepository.existsById(id)) {
            throw new MonthlyPlanNotFoundException(id);
        }
        monthlyPlanRepository.deleteById(id);
    }

    public MonthlyPlanDTO getMonthlyPlan(Long id) {
        MonthlyPlan monthlyPlan = monthlyPlanRepository.findByIdWithGradePlans(id)
            .orElseThrow(() -> new MonthlyPlanNotFoundException(id));
        return MonthlyPlanDTO.fromDomain(monthlyPlan);
    }

    public List<MonthlyPlanDTO> getMonthlyPlansByProject(Long projectId) {
        List<MonthlyPlan> monthlyPlans = monthlyPlanRepository.findByProjectId(projectId);
        return monthlyPlans.stream()
            .map(MonthlyPlanDTO::fromDomain)
            .collect(Collectors.toList());
    }

    public List<MonthlyPlanDTO> getMonthlyPlansByProjectAndYear(Long projectId, Integer year) {
        List<MonthlyPlan> monthlyPlans = monthlyPlanRepository
            .findByProjectIdAndYearWithGradePlans(projectId, year);
        return monthlyPlans.stream()
            .map(MonthlyPlanDTO::fromDomain)
            .collect(Collectors.toList());
    }

    public List<Integer> getAvailableYears() {
        return monthlyPlanRepository.findDistinctYears();
    }

    public List<String> getAvailableGrades() {
        return gradePlanRepository.findDistinctGrades();
    }

    private void validateNewMonthlyPlan(MonthlyPlanDTO monthlyPlanDTO) {
        // 동일한 프로젝트의 동일 연월에 대한 계획이 이미 존재하는지 확인
        monthlyPlanRepository.findByProjectIdAndYearAndMonth(
                monthlyPlanDTO.getProjectId(),
                monthlyPlanDTO.getYear(),
                monthlyPlanDTO.getMonth())
            .ifPresent(existingPlan -> {
                throw new DuplicateMonthlyPlanException(
                    String.format("Monthly plan already exists for project %d in %d-%02d",
                        monthlyPlanDTO.getProjectId(),
                        monthlyPlanDTO.getYear(),
                        monthlyPlanDTO.getMonth()));
            });

        validateMonthlyPlanData(monthlyPlanDTO);
    }

    private void validateUpdateMonthlyPlan(Long id, MonthlyPlanDTO monthlyPlanDTO) {
        // 다른 프로젝트의 동일 연월에 대한 계획이 이미 존재하는지 확인
        monthlyPlanRepository.findByProjectIdAndYearAndMonth(
                monthlyPlanDTO.getProjectId(),
                monthlyPlanDTO.getYear(),
                monthlyPlanDTO.getMonth())
            .ifPresent(existingPlan -> {
                if (!existingPlan.getId().equals(id)) {
                    throw new DuplicateMonthlyPlanException(
                        String.format("Monthly plan already exists for project %d in %d-%02d",
                            monthlyPlanDTO.getProjectId(),
                            monthlyPlanDTO.getYear(),
                            monthlyPlanDTO.getMonth()));
                }
            });

        validateMonthlyPlanData(monthlyPlanDTO);
    }

    private void validateMonthlyPlanData(MonthlyPlanDTO monthlyPlanDTO) {
        if (monthlyPlanDTO.getGradePlans() == null || monthlyPlanDTO.getGradePlans().isEmpty()) {
            throw new InvalidMonthlyPlanException("At least one grade plan is required");
        }

        // 등급별 계획의 유효성 검사
        monthlyPlanDTO.getGradePlans().forEach(gradePlanDTO -> {
            if (gradePlanDTO.getPlannedCount() < 0) {
                throw new InvalidMonthlyPlanException(
                    String.format("Planned count must be non-negative for grade %s",
                        gradePlanDTO.getGrade()));
            }
            if (gradePlanDTO.getActualCount() != null && gradePlanDTO.getActualCount() < 0) {
                throw new InvalidMonthlyPlanException(
                    String.format("Actual count must be non-negative for grade %s",
                        gradePlanDTO.getGrade()));
            }
        });
    }

    // KPI 데이터 조회
    public KpiData getKpiData(Long projectId, Integer year, Integer month) {
        // Implementation of getKpiData method
        return null; // Placeholder return, actual implementation needed
    }
} 