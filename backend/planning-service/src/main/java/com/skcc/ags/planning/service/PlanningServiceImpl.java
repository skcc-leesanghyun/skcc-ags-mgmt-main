package com.skcc.ags.planning.service;

import com.skcc.ags.planning.domain.GradePlan;
import com.skcc.ags.planning.domain.KpiData;
import com.skcc.ags.planning.domain.MonthlyPlan;
import com.skcc.ags.planning.mapper.MonthlyPlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PlanningServiceImpl implements PlanningService {

    private final MonthlyPlanMapper monthlyPlanMapper;

    public PlanningServiceImpl(MonthlyPlanMapper monthlyPlanMapper) {
        this.monthlyPlanMapper = monthlyPlanMapper;
    }

    @Override
    public List<MonthlyPlan> getMonthlyPlans(Long projectId, Integer year, Integer month) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("year", year);
        params.put("month", month);
        return monthlyPlanMapper.selectMonthlyPlans(params);
    }

    @Override
    public MonthlyPlan getMonthlyPlan(Long id) {
        return monthlyPlanMapper.selectMonthlyPlanById(id);
    }

    @Override
    @Transactional
    public MonthlyPlan createMonthlyPlan(MonthlyPlan monthlyPlan) {
        // 1. 월별 계획 등록
        monthlyPlanMapper.insertMonthlyPlan(monthlyPlan);
        
        // 2. 등급별 계획 등록
        if (monthlyPlan.getGradePlans() != null) {
            for (GradePlan gradePlan : monthlyPlan.getGradePlans()) {
                gradePlan.setMonthlyPlanId(monthlyPlan.getId());
                monthlyPlanMapper.insertGradePlan(gradePlan);
            }
        }
        
        return getMonthlyPlan(monthlyPlan.getId());
    }

    @Override
    @Transactional
    public MonthlyPlan updateMonthlyPlan(Long id, MonthlyPlan monthlyPlan) {
        // 1. ID 검증
        MonthlyPlan existingPlan = getMonthlyPlan(id);
        if (existingPlan == null) {
            throw new IllegalArgumentException("Monthly plan not found with id: " + id);
        }

        // 2. ID 설정
        monthlyPlan.setId(id);
        
        // 3. 월별 계획 수정
        monthlyPlanMapper.updateMonthlyPlan(monthlyPlan);
        
        // 4. 기존 등급별 계획 삭제
        monthlyPlanMapper.deleteGradePlansByMonthlyPlanId(id);
        
        // 5. 새로운 등급별 계획 등록
        if (monthlyPlan.getGradePlans() != null) {
            for (GradePlan gradePlan : monthlyPlan.getGradePlans()) {
                gradePlan.setMonthlyPlanId(id);
                monthlyPlanMapper.insertGradePlan(gradePlan);
            }
        }
        
        return getMonthlyPlan(id);
    }

    @Override
    @Transactional
    public void deleteMonthlyPlan(Long id) {
        // 1. 등급별 계획 삭제
        monthlyPlanMapper.deleteGradePlansByMonthlyPlanId(id);
        
        // 2. 월별 계획 삭제
        monthlyPlanMapper.deleteMonthlyPlan(id);
    }

    @Override
    public KpiData getKpiData(Long projectId, Integer year, Integer month) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("year", year);
        params.put("month", month);
        return monthlyPlanMapper.selectKpiData(params);
    }
} 