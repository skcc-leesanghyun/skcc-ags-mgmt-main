package com.skcc.ags.planning.mapper;

import com.skcc.ags.planning.domain.GradePlan;
import com.skcc.ags.planning.domain.MonthlyPlan;
import com.skcc.ags.planning.dto.GradePlanDTO;
import com.skcc.ags.planning.dto.MonthlyPlanDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanningMapper {

    // MonthlyPlan 엔티티를 DTO로 변환
    public MonthlyPlanDTO toMonthlyPlanDTO(MonthlyPlan monthlyPlan) {
        if (monthlyPlan == null) {
            return null;
        }

        List<GradePlanDTO> gradePlanDTOs = monthlyPlan.getGradePlans().stream()
                .map(this::toGradePlanDTO)
                .collect(Collectors.toList());

        return MonthlyPlanDTO.builder()
                .id(monthlyPlan.getId())
                .projectId(monthlyPlan.getProjectId())
                .projectName(monthlyPlan.getProjectName())
                .planYear(monthlyPlan.getPlanYear())
                .planMonth(monthlyPlan.getPlanMonth())
                .gradePlans(gradePlanDTOs)
                .createdAt(monthlyPlan.getCreatedAt())
                .updatedAt(monthlyPlan.getUpdatedAt())
                .build();
    }

    // MonthlyPlanDTO를 엔티티로 변환
    public MonthlyPlan toMonthlyPlan(MonthlyPlanDTO monthlyPlanDTO) {
        if (monthlyPlanDTO == null) {
            return null;
        }

        List<GradePlan> gradePlans = monthlyPlanDTO.getGradePlans().stream()
                .map(this::toGradePlan)
                .collect(Collectors.toList());

        MonthlyPlan monthlyPlan = MonthlyPlan.builder()
                .id(monthlyPlanDTO.getId())
                .projectId(monthlyPlanDTO.getProjectId())
                .projectName(monthlyPlanDTO.getProjectName())
                .planYear(monthlyPlanDTO.getPlanYear())
                .planMonth(monthlyPlanDTO.getPlanMonth())
                .build();

        // 양방향 관계 설정
        gradePlans.forEach(monthlyPlan::addGradePlan);

        return monthlyPlan;
    }

    // GradePlan 엔티티를 DTO로 변환
    public GradePlanDTO toGradePlanDTO(GradePlan gradePlan) {
        if (gradePlan == null) {
            return null;
        }

        return GradePlanDTO.builder()
                .id(gradePlan.getId())
                .grade(gradePlan.getGrade())
                .plannedCount(gradePlan.getPlannedCount())
                .actualCount(gradePlan.getActualCount())
                .build();
    }

    // GradePlanDTO를 엔티티로 변환
    public GradePlan toGradePlan(GradePlanDTO gradePlanDTO) {
        if (gradePlanDTO == null) {
            return null;
        }

        return GradePlan.builder()
                .id(gradePlanDTO.getId())
                .grade(gradePlanDTO.getGrade())
                .plannedCount(gradePlanDTO.getPlannedCount())
                .actualCount(gradePlanDTO.getActualCount())
                .build();
    }

    // MonthlyPlan 엔티티 리스트를 DTO 리스트로 변환
    public List<MonthlyPlanDTO> toMonthlyPlanDTOList(List<MonthlyPlan> monthlyPlans) {
        return monthlyPlans.stream()
                .map(this::toMonthlyPlanDTO)
                .collect(Collectors.toList());
    }

    // MonthlyPlanDTO 리스트를 엔티티 리스트로 변환
    public List<MonthlyPlan> toMonthlyPlanList(List<MonthlyPlanDTO> monthlyPlanDTOs) {
        return monthlyPlanDTOs.stream()
                .map(this::toMonthlyPlan)
                .collect(Collectors.toList());
    }

    // GradePlan 엔티티 리스트를 DTO 리스트로 변환
    public List<GradePlanDTO> toGradePlanDTOList(List<GradePlan> gradePlans) {
        return gradePlans.stream()
                .map(this::toGradePlanDTO)
                .collect(Collectors.toList());
    }

    // GradePlanDTO 리스트를 엔티티 리스트로 변환
    public List<GradePlan> toGradePlanList(List<GradePlanDTO> gradePlanDTOs) {
        return gradePlanDTOs.stream()
                .map(this::toGradePlan)
                .collect(Collectors.toList());
    }
} 