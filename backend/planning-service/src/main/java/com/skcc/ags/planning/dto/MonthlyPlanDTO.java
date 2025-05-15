package com.skcc.ags.planning.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyPlanDTO {
    private Long id;

    @NotNull(message = "프로젝트 ID는 필수입니다.")
    private Long projectId;

    private String projectName;

    @NotNull(message = "연도는 필수입니다.")
    @Min(value = 2000, message = "연도는 2000년 이상이어야 합니다.")
    private Integer planYear;

    @NotNull(message = "월은 필수입니다.")
    @Min(value = 1, message = "월은 1 이상이어야 합니다.")
    private Integer planMonth;

    @NotEmpty(message = "등급별 계획은 필수입니다.")
    @Valid
    private List<GradePlanDTO> gradePlans;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // 전체 계획 인력 수 계산
    public int calculateTotalPlannedCount() {
        return gradePlans.stream()
                .mapToInt(GradePlanDTO::getPlannedCount)
                .sum();
    }

    // 전체 실제 인력 수 계산
    public int calculateTotalActualCount() {
        return gradePlans.stream()
                .mapToInt(GradePlanDTO::getActualCount)
                .sum();
    }

    // 달성률 계산 (실제/계획 * 100)
    public double calculateAchievementRate() {
        int totalPlanned = calculateTotalPlannedCount();
        if (totalPlanned == 0) {
            return 0.0;
        }
        return ((double) calculateTotalActualCount() / totalPlanned) * 100;
    }

    // 활용률 계산 (실제/(계획+실제) * 100)
    public double calculateUtilizationRate() {
        int totalPlanned = calculateTotalPlannedCount();
        int totalActual = calculateTotalActualCount();
        int total = totalPlanned + totalActual;
        
        if (total == 0) {
            return 0.0;
        }
        return ((double) totalActual / total) * 100;
    }
} 