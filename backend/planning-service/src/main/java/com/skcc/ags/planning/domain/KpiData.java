package com.skcc.ags.planning.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiData {
    private Long projectId;
    private Integer year;
    private Integer month;
    
    // 전체 계획 인력
    private int totalPlannedManpower;
    
    // 전체 실제 인력
    private int totalActualManpower;
    
    // 등급별 계획 인력
    private int[] plannedManpowerByGrade;
    
    // 등급별 실제 인력
    private int[] actualManpowerByGrade;
    
    // 전체 달성률 (실제/계획 * 100)
    private double achievementRate;
    
    // 등급별 달성률
    private double[] achievementRateByGrade;
    
    // 전체 활용률 (실제/(계획+실제) * 100)
    private double utilizationRate;
    
    // 등급별 활용률
    private double[] utilizationRateByGrade;
    
    // KPI 상태 (목표 달성 여부)
    private KpiStatus status;
    
    public enum KpiStatus {
        EXCEEDED,    // 초과 달성
        ACHIEVED,    // 달성
        IN_PROGRESS, // 진행중
        AT_RISK,    // 위험
        FAILED      // 미달성
    }
    
    // KPI 상태 계산 메서드
    public void calculateStatus() {
        if (achievementRate >= 100) {
            this.status = KpiStatus.ACHIEVED;
            if (achievementRate > 110) {
                this.status = KpiStatus.EXCEEDED;
            }
        } else if (achievementRate >= 90) {
            this.status = KpiStatus.IN_PROGRESS;
        } else if (achievementRate >= 80) {
            this.status = KpiStatus.AT_RISK;
        } else {
            this.status = KpiStatus.FAILED;
        }
    }
    
    // 달성률 계산 메서드
    public void calculateAchievementRates() {
        // 전체 달성률 계산
        if (totalPlannedManpower > 0) {
            this.achievementRate = ((double) totalActualManpower / totalPlannedManpower) * 100;
        }
        
        // 등급별 달성률 계산
        this.achievementRateByGrade = new double[plannedManpowerByGrade.length];
        for (int i = 0; i < plannedManpowerByGrade.length; i++) {
            if (plannedManpowerByGrade[i] > 0) {
                achievementRateByGrade[i] = ((double) actualManpowerByGrade[i] / plannedManpowerByGrade[i]) * 100;
            }
        }
    }
    
    // 활용률 계산 메서드
    public void calculateUtilizationRates() {
        // 전체 활용률 계산
        int totalManpower = totalPlannedManpower + totalActualManpower;
        if (totalManpower > 0) {
            this.utilizationRate = ((double) totalActualManpower / totalManpower) * 100;
        }
        
        // 등급별 활용률 계산
        this.utilizationRateByGrade = new double[plannedManpowerByGrade.length];
        for (int i = 0; i < plannedManpowerByGrade.length; i++) {
            int totalGradeManpower = plannedManpowerByGrade[i] + actualManpowerByGrade[i];
            if (totalGradeManpower > 0) {
                utilizationRateByGrade[i] = ((double) actualManpowerByGrade[i] / totalGradeManpower) * 100;
            }
        }
    }
} 