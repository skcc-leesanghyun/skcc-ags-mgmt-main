package com.skcc.ags.planning.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.ags.planning.domain.KpiData;
import com.skcc.ags.planning.dto.GradePlanDTO;
import com.skcc.ags.planning.dto.MonthlyPlanDTO;
import com.skcc.ags.planning.exception.DuplicateMonthlyPlanException;
import com.skcc.ags.planning.exception.MonthlyPlanNotFoundException;
import com.skcc.ags.planning.service.PlanningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanningController.class)
class PlanningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanningService planningService;

    private MonthlyPlanDTO monthlyPlanDTO;
    private GradePlanDTO gradePlanDTO;
    private KpiData kpiData;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        
        gradePlanDTO = GradePlanDTO.builder()
                .id(1L)
                .grade("Senior")
                .plannedCount(5)
                .actualCount(4)
                .build();

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

        kpiData = KpiData.builder()
                .projectId(100L)
                .year(2024)
                .month(3)
                .totalPlannedManpower(5)
                .totalActualManpower(4)
                .build();
    }

    @Test
    @WithMockUser(roles = "PLANNING_MANAGER")
    @DisplayName("월간 계획 생성 API")
    void createMonthlyPlan() throws Exception {
        when(planningService.createMonthlyPlan(any(MonthlyPlanDTO.class)))
                .thenReturn(monthlyPlanDTO);

        mockMvc.perform(post("/api/v1/planning/monthly-plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(monthlyPlanDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(monthlyPlanDTO.getId()))
                .andExpect(jsonPath("$.projectId").value(monthlyPlanDTO.getProjectId()));

        verify(planningService).createMonthlyPlan(any(MonthlyPlanDTO.class));
    }

    @Test
    @WithMockUser(roles = "PLANNING_MANAGER")
    @DisplayName("월간 계획 생성 실패 - 중복")
    void createMonthlyPlan_Duplicate() throws Exception {
        when(planningService.createMonthlyPlan(any(MonthlyPlanDTO.class)))
                .thenThrow(new DuplicateMonthlyPlanException("이미 존재하는 월간 계획입니다."));

        mockMvc.perform(post("/api/v1/planning/monthly-plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(monthlyPlanDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "PLANNING_MANAGER")
    @DisplayName("월간 계획 수정 API")
    void updateMonthlyPlan() throws Exception {
        when(planningService.updateMonthlyPlan(eq(1L), any(MonthlyPlanDTO.class)))
                .thenReturn(monthlyPlanDTO);

        mockMvc.perform(put("/api/v1/planning/monthly-plans/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(monthlyPlanDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(monthlyPlanDTO.getId()));

        verify(planningService).updateMonthlyPlan(eq(1L), any(MonthlyPlanDTO.class));
    }

    @Test
    @WithMockUser(roles = "PLANNING_MANAGER")
    @DisplayName("월간 계획 수정 실패 - 존재하지 않음")
    void updateMonthlyPlan_NotFound() throws Exception {
        when(planningService.updateMonthlyPlan(eq(999L), any(MonthlyPlanDTO.class)))
                .thenThrow(new MonthlyPlanNotFoundException("존재하지 않는 월간 계획입니다."));

        mockMvc.perform(put("/api/v1/planning/monthly-plans/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(monthlyPlanDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "PLANNING_MANAGER")
    @DisplayName("월간 계획 삭제 API")
    void deleteMonthlyPlan() throws Exception {
        doNothing().when(planningService).deleteMonthlyPlan(1L);

        mockMvc.perform(delete("/api/v1/planning/monthly-plans/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(planningService).deleteMonthlyPlan(1L);
    }

    @Test
    @WithMockUser(roles = "PLANNING_VIEWER")
    @DisplayName("월간 계획 조회 API")
    void getMonthlyPlan() throws Exception {
        when(planningService.getMonthlyPlan(1L)).thenReturn(monthlyPlanDTO);

        mockMvc.perform(get("/api/v1/planning/monthly-plans/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(monthlyPlanDTO.getId()))
                .andExpect(jsonPath("$.projectId").value(monthlyPlanDTO.getProjectId()));
    }

    @Test
    @WithMockUser(roles = "PLANNING_VIEWER")
    @DisplayName("프로젝트별 월간 계획 목록 조회 API")
    void getMonthlyPlansByProject() throws Exception {
        List<MonthlyPlanDTO> monthlyPlans = Arrays.asList(monthlyPlanDTO);
        when(planningService.getMonthlyPlansByProject(100L)).thenReturn(monthlyPlans);

        mockMvc.perform(get("/api/v1/planning/projects/{projectId}/monthly-plans", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].projectId").value(monthlyPlanDTO.getProjectId()));
    }

    @Test
    @WithMockUser(roles = "PLANNING_VIEWER")
    @DisplayName("프로젝트와 연도별 월간 계획 목록 조회 API")
    void getMonthlyPlansByProjectAndYear() throws Exception {
        List<MonthlyPlanDTO> monthlyPlans = Arrays.asList(monthlyPlanDTO);
        when(planningService.getMonthlyPlansByProjectAndYear(100L, 2024)).thenReturn(monthlyPlans);

        mockMvc.perform(get("/api/v1/planning/projects/{projectId}/monthly-plans/year/{year}", 100L, 2024))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].projectId").value(monthlyPlanDTO.getProjectId()))
                .andExpect(jsonPath("$[0].planYear").value(monthlyPlanDTO.getPlanYear()));
    }

    @Test
    @WithMockUser(roles = "PLANNING_VIEWER")
    @DisplayName("사용 가능한 연도 목록 조회 API")
    void getAvailableYears() throws Exception {
        List<Integer> years = Arrays.asList(2023, 2024);
        when(planningService.getAvailableYears()).thenReturn(years);

        mockMvc.perform(get("/api/v1/planning/years"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").value(2023))
                .andExpect(jsonPath("$[1]").value(2024));
    }

    @Test
    @WithMockUser(roles = "PLANNING_VIEWER")
    @DisplayName("사용 가능한 등급 목록 조회 API")
    void getAvailableGrades() throws Exception {
        List<String> grades = Arrays.asList("Junior", "Senior");
        when(planningService.getAvailableGrades()).thenReturn(grades);

        mockMvc.perform(get("/api/v1/planning/grades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").value("Junior"))
                .andExpect(jsonPath("$[1]").value("Senior"));
    }

    @Test
    @WithMockUser(roles = "PLANNING_VIEWER")
    @DisplayName("KPI 데이터 조회 API")
    void getKpiData() throws Exception {
        when(planningService.getKpiData(100L, 2024, 3)).thenReturn(kpiData);

        mockMvc.perform(get("/api/v1/planning/projects/{projectId}/kpi", 100L)
                .param("year", "2024")
                .param("month", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(kpiData.getProjectId()))
                .andExpect(jsonPath("$.year").value(kpiData.getYear()))
                .andExpect(jsonPath("$.month").value(kpiData.getMonth()));
    }

    @Test
    @DisplayName("인증되지 않은 사용자 접근 제한")
    void unauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/planning/monthly-plans/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "PLANNING_VIEWER")
    @DisplayName("권한 없는 작업 시도")
    void forbidden() throws Exception {
        mockMvc.perform(post("/api/v1/planning/monthly-plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(monthlyPlanDTO)))
                .andExpect(status().isForbidden());
    }
} 