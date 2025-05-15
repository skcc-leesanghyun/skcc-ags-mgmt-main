package com.skcc.ags.planning.dto;

import com.skcc.ags.planning.domain.GradePlan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "등급별 계획 DTO")
public class GradePlanDTO {
    
    @Schema(description = "등급별 계획 ID")
    private Long id;

    @Schema(description = "월별 계획 ID")
    private Long monthlyPlanId;

    @NotBlank(message = "등급은 필수입니다")
    @Schema(description = "등급", required = true)
    private String grade;

    @NotNull(message = "계획 인원은 필수입니다")
    @Min(value = 0, message = "계획 인원은 0 이상이어야 합니다")
    @Schema(description = "계획 인원", required = true)
    private Integer plannedCount;

    @NotNull(message = "실제 인원은 필수입니다")
    @Min(value = 0, message = "실제 인원은 0 이상이어야 합니다")
    @Schema(description = "실제 인원", required = true)
    private Integer actualCount;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMonthlyPlanId() {
        return monthlyPlanId;
    }

    public void setMonthlyPlanId(Long monthlyPlanId) {
        this.monthlyPlanId = monthlyPlanId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getPlannedCount() {
        return plannedCount;
    }

    public void setPlannedCount(Integer plannedCount) {
        this.plannedCount = plannedCount;
    }

    public Integer getActualCount() {
        return actualCount;
    }

    public void setActualCount(Integer actualCount) {
        this.actualCount = actualCount;
    }

    // Convert DTO to Domain
    public GradePlan toDomain() {
        GradePlan gradePlan = new GradePlan();
        gradePlan.setId(this.id);
        gradePlan.setMonthlyPlanId(this.monthlyPlanId);
        gradePlan.setGrade(this.grade);
        gradePlan.setPlannedCount(this.plannedCount);
        gradePlan.setActualCount(this.actualCount);
        return gradePlan;
    }

    // Convert Domain to DTO
    public static GradePlanDTO fromDomain(GradePlan gradePlan) {
        if (gradePlan == null) {
            return null;
        }

        GradePlanDTO dto = new GradePlanDTO();
        dto.setId(gradePlan.getId());
        dto.setMonthlyPlanId(gradePlan.getMonthlyPlanId());
        dto.setGrade(gradePlan.getGrade());
        dto.setPlannedCount(gradePlan.getPlannedCount());
        dto.setActualCount(gradePlan.getActualCount());
        return dto;
    }

    // 달성률 계산 (실제/계획 * 100)
    public double calculateAchievementRate() {
        if (plannedCount == 0) {
            return 0.0;
        }
        return ((double) actualCount / plannedCount) * 100;
    }

    // 활용률 계산 (실제/(계획+실제) * 100)
    public double calculateUtilizationRate() {
        int total = plannedCount + actualCount;
        if (total == 0) {
            return 0.0;
        }
        return ((double) actualCount / total) * 100;
    }

    // 차이 계산 (실제 - 계획)
    public int calculateDifference() {
        return actualCount - plannedCount;
    }

    // 달성 상태 확인
    public boolean isAchieved() {
        return calculateAchievementRate() >= 100;
    }

    // 위험 상태 확인 (달성률 80% 미만)
    public boolean isAtRisk() {
        double achievementRate = calculateAchievementRate();
        return achievementRate < 80;
    }
} 