package com.skcc.ags.planning.service;

import com.skcc.ags.planning.domain.GradePlan;
import com.skcc.ags.planning.domain.MonthlyPlan;
import com.skcc.ags.planning.dto.GradePlanDTO;
import com.skcc.ags.planning.dto.MonthlyPlanDTO;
import com.skcc.ags.planning.exception.DuplicateMonthlyPlanException;
import com.skcc.ags.planning.exception.MonthlyPlanNotFoundException;
import com.skcc.ags.planning.mapper.PlanningMapper;
import com.skcc.ags.planning.repository.MonthlyPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanningServiceTest {

    @Mock
    private MonthlyPlanRepository monthlyPlanRepository;

    @Mock
    private PlanningMapper planningMapper;

    @InjectMocks
    private PlanningService planningService;

    private MonthlyPlan monthlyPlan;
    private MonthlyPlanDTO monthlyPlanDTO;
    private GradePlan gradePlan;
    private GradePlanDTO gradePlanDTO;

    @BeforeEach
    void setUp() {
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
    @DisplayName("월간 계획 생성 성공")
    void createMonthlyPlan_Success() {
        // given
        when(monthlyPlanRepository.findByProjectIdAndPlanYearAndPlanMonth(
                monthlyPlanDTO.getProjectId(),
                monthlyPlanDTO.getPlanYear(),
                monthlyPlanDTO.getPlanMonth()
        )).thenReturn(Optional.empty());
        when(planningMapper.toMonthlyPlan(monthlyPlanDTO)).thenReturn(monthlyPlan);
        when(monthlyPlanRepository.save(any(MonthlyPlan.class))).thenReturn(monthlyPlan);
        when(planningMapper.toMonthlyPlanDTO(monthlyPlan)).thenReturn(monthlyPlanDTO);

        // when
        MonthlyPlanDTO result = planningService.createMonthlyPlan(monthlyPlanDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getProjectId()).isEqualTo(monthlyPlanDTO.getProjectId());
        verify(monthlyPlanRepository).save(any(MonthlyPlan.class));
    }

    @Test
    @DisplayName("월간 계획 생성 실패 - 중복")
    void createMonthlyPlan_DuplicateFailure() {
        // given
        when(monthlyPlanRepository.findByProjectIdAndPlanYearAndPlanMonth(
                monthlyPlanDTO.getProjectId(),
                monthlyPlanDTO.getPlanYear(),
                monthlyPlanDTO.getPlanMonth()
        )).thenReturn(Optional.of(monthlyPlan));

        // when & then
        assertThatThrownBy(() -> planningService.createMonthlyPlan(monthlyPlanDTO))
                .isInstanceOf(DuplicateMonthlyPlanException.class);
    }

    @Test
    @DisplayName("월간 계획 수정 성공")
    void updateMonthlyPlan_Success() {
        // given
        Long id = 1L;
        when(monthlyPlanRepository.findById(id)).thenReturn(Optional.of(monthlyPlan));
        when(monthlyPlanRepository.findByProjectIdAndPlanYearAndPlanMonth(
                monthlyPlanDTO.getProjectId(),
                monthlyPlanDTO.getPlanYear(),
                monthlyPlanDTO.getPlanMonth()
        )).thenReturn(Optional.empty());
        when(planningMapper.toMonthlyPlan(monthlyPlanDTO)).thenReturn(monthlyPlan);
        when(monthlyPlanRepository.save(any(MonthlyPlan.class))).thenReturn(monthlyPlan);
        when(planningMapper.toMonthlyPlanDTO(monthlyPlan)).thenReturn(monthlyPlanDTO);

        // when
        MonthlyPlanDTO result = planningService.updateMonthlyPlan(id, monthlyPlanDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(monthlyPlanRepository).save(any(MonthlyPlan.class));
    }

    @Test
    @DisplayName("월간 계획 수정 실패 - 존재하지 않는 계획")
    void updateMonthlyPlan_NotFoundFailure() {
        // given
        Long id = 999L;
        when(monthlyPlanRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> planningService.updateMonthlyPlan(id, monthlyPlanDTO))
                .isInstanceOf(MonthlyPlanNotFoundException.class);
    }

    @Test
    @DisplayName("월간 계획 삭제 성공")
    void deleteMonthlyPlan_Success() {
        // given
        Long id = 1L;
        when(monthlyPlanRepository.findById(id)).thenReturn(Optional.of(monthlyPlan));

        // when
        planningService.deleteMonthlyPlan(id);

        // then
        verify(monthlyPlanRepository).delete(monthlyPlan);
    }

    @Test
    @DisplayName("월간 계획 삭제 실패 - 존재하지 않는 계획")
    void deleteMonthlyPlan_NotFoundFailure() {
        // given
        Long id = 999L;
        when(monthlyPlanRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> planningService.deleteMonthlyPlan(id))
                .isInstanceOf(MonthlyPlanNotFoundException.class);
    }

    @Test
    @DisplayName("월간 계획 조회 성공")
    void getMonthlyPlan_Success() {
        // given
        Long id = 1L;
        when(monthlyPlanRepository.findById(id)).thenReturn(Optional.of(monthlyPlan));
        when(planningMapper.toMonthlyPlanDTO(monthlyPlan)).thenReturn(monthlyPlanDTO);

        // when
        MonthlyPlanDTO result = planningService.getMonthlyPlan(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("프로젝트별 월간 계획 목록 조회")
    void getMonthlyPlansByProject_Success() {
        // given
        Long projectId = 100L;
        List<MonthlyPlan> monthlyPlans = Arrays.asList(monthlyPlan);
        List<MonthlyPlanDTO> monthlyPlanDTOs = Arrays.asList(monthlyPlanDTO);

        when(monthlyPlanRepository.findByProjectId(projectId)).thenReturn(monthlyPlans);
        when(planningMapper.toMonthlyPlanDTOList(monthlyPlans)).thenReturn(monthlyPlanDTOs);

        // when
        List<MonthlyPlanDTO> results = planningService.getMonthlyPlansByProject(projectId);

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProjectId()).isEqualTo(projectId);
    }

    @Test
    @DisplayName("프로젝트와 연도별 월간 계획 목록 조회")
    void getMonthlyPlansByProjectAndYear_Success() {
        // given
        Long projectId = 100L;
        Integer year = 2024;
        List<MonthlyPlan> monthlyPlans = Arrays.asList(monthlyPlan);
        List<MonthlyPlanDTO> monthlyPlanDTOs = Arrays.asList(monthlyPlanDTO);

        when(monthlyPlanRepository.findByProjectIdAndPlanYear(projectId, year)).thenReturn(monthlyPlans);
        when(planningMapper.toMonthlyPlanDTOList(monthlyPlans)).thenReturn(monthlyPlanDTOs);

        // when
        List<MonthlyPlanDTO> results = planningService.getMonthlyPlansByProjectAndYear(projectId, year);

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProjectId()).isEqualTo(projectId);
        assertThat(results.get(0).getPlanYear()).isEqualTo(year);
    }

    @Test
    @DisplayName("사용 가능한 연도 목록 조회")
    void getAvailableYears_Success() {
        // given
        List<Integer> years = Arrays.asList(2023, 2024);
        when(monthlyPlanRepository.findDistinctPlanYears()).thenReturn(years);

        // when
        List<Integer> results = planningService.getAvailableYears();

        // then
        assertThat(results).hasSize(2);
        assertThat(results).containsExactly(2023, 2024);
    }

    @Test
    @DisplayName("사용 가능한 등급 목록 조회")
    void getAvailableGrades_Success() {
        // given
        List<String> grades = Arrays.asList("Junior", "Senior");
        when(monthlyPlanRepository.findDistinctGrades()).thenReturn(grades);

        // when
        List<String> results = planningService.getAvailableGrades();

        // then
        assertThat(results).hasSize(2);
        assertThat(results).containsExactly("Junior", "Senior");
    }
} 