package com.skcc.ags.planning.mapper;

import com.skcc.ags.planning.domain.GradePlan;
import com.skcc.ags.planning.domain.MonthlyPlan;
import com.skcc.ags.planning.dto.GradePlanDTO;
import com.skcc.ags.planning.dto.MonthlyPlanDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlanningMapperTest {

    private PlanningMapper mapper;
    private MonthlyPlan monthlyPlan;
    private MonthlyPlanDTO monthlyPlanDTO;
    private GradePlan gradePlan;
    private GradePlanDTO gradePlanDTO;

    @BeforeEach
    void setUp() {
        mapper = new PlanningMapper();

        // GradePlan 테스트 데이터 설정
        gradePlan = GradePlan.builder()
                .id(1L)
                .grade("Senior")
                .plannedCount(5)
                .actualCount(4)
                .build();

        gradePlanDTO = GradePlanDTO.builder()
                .id(1L)
                .grade("Senior")
                .plannedCount(5)
                .actualCount(4)
                .build();

        // MonthlyPlan 테스트 데이터 설정
        LocalDateTime now = LocalDateTime.now();
        monthlyPlan = MonthlyPlan.builder()
                .id(1L)
                .projectId(100L)
                .projectName("Test Project")
                .planYear(2024)
                .planMonth(3)
                .build();
        monthlyPlan.addGradePlan(gradePlan);
        monthlyPlan.setCreatedAt(now);
        monthlyPlan.setUpdatedAt(now);

        monthlyPlanDTO = MonthlyPlanDTO.builder()
                .id(1L)
                .projectId(100L)
                .projectName("Test Project")
                .planYear(2024)
                .planMonth(3)
                .gradePlans(Arrays.asList(gradePlanDTO))
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    @Test
    @DisplayName("GradePlan 엔티티를 DTO로 변환")
    void toGradePlanDTO_ShouldMapCorrectly() {
        // when
        GradePlanDTO result = mapper.toGradePlanDTO(gradePlan);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(gradePlan.getId());
        assertThat(result.getGrade()).isEqualTo(gradePlan.getGrade());
        assertThat(result.getPlannedCount()).isEqualTo(gradePlan.getPlannedCount());
        assertThat(result.getActualCount()).isEqualTo(gradePlan.getActualCount());
    }

    @Test
    @DisplayName("GradePlanDTO를 엔티티로 변환")
    void toGradePlan_ShouldMapCorrectly() {
        // when
        GradePlan result = mapper.toGradePlan(gradePlanDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(gradePlanDTO.getId());
        assertThat(result.getGrade()).isEqualTo(gradePlanDTO.getGrade());
        assertThat(result.getPlannedCount()).isEqualTo(gradePlanDTO.getPlannedCount());
        assertThat(result.getActualCount()).isEqualTo(gradePlanDTO.getActualCount());
    }

    @Test
    @DisplayName("MonthlyPlan 엔티티를 DTO로 변환")
    void toMonthlyPlanDTO_ShouldMapCorrectly() {
        // when
        MonthlyPlanDTO result = mapper.toMonthlyPlanDTO(monthlyPlan);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(monthlyPlan.getId());
        assertThat(result.getProjectId()).isEqualTo(monthlyPlan.getProjectId());
        assertThat(result.getProjectName()).isEqualTo(monthlyPlan.getProjectName());
        assertThat(result.getPlanYear()).isEqualTo(monthlyPlan.getPlanYear());
        assertThat(result.getPlanMonth()).isEqualTo(monthlyPlan.getPlanMonth());
        assertThat(result.getCreatedAt()).isEqualTo(monthlyPlan.getCreatedAt());
        assertThat(result.getUpdatedAt()).isEqualTo(monthlyPlan.getUpdatedAt());
        
        // GradePlans 검증
        assertThat(result.getGradePlans()).hasSize(1);
        GradePlanDTO gradePlanResult = result.getGradePlans().get(0);
        assertThat(gradePlanResult.getGrade()).isEqualTo(gradePlan.getGrade());
    }

    @Test
    @DisplayName("MonthlyPlanDTO를 엔티티로 변환")
    void toMonthlyPlan_ShouldMapCorrectly() {
        // when
        MonthlyPlan result = mapper.toMonthlyPlan(monthlyPlanDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(monthlyPlanDTO.getId());
        assertThat(result.getProjectId()).isEqualTo(monthlyPlanDTO.getProjectId());
        assertThat(result.getProjectName()).isEqualTo(monthlyPlanDTO.getProjectName());
        assertThat(result.getPlanYear()).isEqualTo(monthlyPlanDTO.getPlanYear());
        assertThat(result.getPlanMonth()).isEqualTo(monthlyPlanDTO.getPlanMonth());
        
        // GradePlans 검증
        assertThat(result.getGradePlans()).hasSize(1);
        GradePlan gradePlanResult = result.getGradePlans().get(0);
        assertThat(gradePlanResult.getGrade()).isEqualTo(gradePlanDTO.getGrade());
    }

    @Test
    @DisplayName("리스트 변환 테스트")
    void listConversions_ShouldMapCorrectly() {
        // given
        List<MonthlyPlan> monthlyPlans = Arrays.asList(monthlyPlan);
        List<MonthlyPlanDTO> monthlyPlanDTOs = Arrays.asList(monthlyPlanDTO);
        List<GradePlan> gradePlans = Arrays.asList(gradePlan);
        List<GradePlanDTO> gradePlanDTOs = Arrays.asList(gradePlanDTO);

        // when
        List<MonthlyPlanDTO> monthlyPlanDTOResults = mapper.toMonthlyPlanDTOList(monthlyPlans);
        List<MonthlyPlan> monthlyPlanResults = mapper.toMonthlyPlanList(monthlyPlanDTOs);
        List<GradePlanDTO> gradePlanDTOResults = mapper.toGradePlanDTOList(gradePlans);
        List<GradePlan> gradePlanResults = mapper.toGradePlanList(gradePlanDTOs);

        // then
        assertThat(monthlyPlanDTOResults).hasSize(1);
        assertThat(monthlyPlanResults).hasSize(1);
        assertThat(gradePlanDTOResults).hasSize(1);
        assertThat(gradePlanResults).hasSize(1);
    }

    @Test
    @DisplayName("Null 입력 처리 테스트")
    void nullInputs_ShouldReturnNull() {
        // when & then
        assertThat(mapper.toMonthlyPlanDTO(null)).isNull();
        assertThat(mapper.toMonthlyPlan(null)).isNull();
        assertThat(mapper.toGradePlanDTO(null)).isNull();
        assertThat(mapper.toGradePlan(null)).isNull();
    }
} 