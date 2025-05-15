package com.skcc.ags.planning.controller;

import com.skcc.ags.planning.domain.KpiData;
import com.skcc.ags.planning.dto.MonthlyPlanDTO;
import com.skcc.ags.planning.service.PlanningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/planning")
@Tag(name = "Planning", description = "계획 관리 API")
@Validated
public class PlanningController {

    private final PlanningService planningService;

    public PlanningController(PlanningService planningService) {
        this.planningService = planningService;
    }

    @PostMapping("/monthly-plans")
    @Operation(summary = "월별 계획 생성", description = "새로운 월별 계획을 생성합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "월별 계획 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "409", description = "중복된 계획 존재")
    })
    @PreAuthorize("hasRole('PLANNING_MANAGER')")
    public ResponseEntity<MonthlyPlanDTO> createMonthlyPlan(
            @Valid @RequestBody MonthlyPlanDTO monthlyPlanDTO) {
        MonthlyPlanDTO createdPlan = planningService.createMonthlyPlan(monthlyPlanDTO);
        return ResponseEntity.ok(createdPlan);
    }

    @PutMapping("/monthly-plans/{id}")
    @Operation(summary = "월별 계획 수정", description = "기존 월별 계획을 수정합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "월별 계획 수정 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "404", description = "계획을 찾을 수 없음"),
        @ApiResponse(responseCode = "409", description = "중복된 계획 존재")
    })
    @PreAuthorize("hasRole('PLANNING_MANAGER')")
    public ResponseEntity<MonthlyPlanDTO> updateMonthlyPlan(
            @Parameter(description = "월별 계획 ID") @PathVariable Long id,
            @Valid @RequestBody MonthlyPlanDTO monthlyPlanDTO) {
        MonthlyPlanDTO updatedPlan = planningService.updateMonthlyPlan(id, monthlyPlanDTO);
        return ResponseEntity.ok(updatedPlan);
    }

    @DeleteMapping("/monthly-plans/{id}")
    @Operation(summary = "월별 계획 삭제", description = "월별 계획을 삭제합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "월별 계획 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "계획을 찾을 수 없음")
    })
    @PreAuthorize("hasRole('PLANNING_MANAGER')")
    public ResponseEntity<Void> deleteMonthlyPlan(
            @Parameter(description = "월별 계획 ID") @PathVariable Long id) {
        planningService.deleteMonthlyPlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/monthly-plans/{id}")
    @Operation(summary = "월별 계획 조회", description = "특정 월별 계획의 상세 정보를 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "월별 계획 조회 성공"),
        @ApiResponse(responseCode = "404", description = "계획을 찾을 수 없음")
    })
    @PreAuthorize("hasAnyRole('PLANNING_MANAGER', 'PLANNING_VIEWER')")
    public ResponseEntity<MonthlyPlanDTO> getMonthlyPlan(
            @Parameter(description = "월별 계획 ID") @PathVariable Long id) {
        MonthlyPlanDTO monthlyPlan = planningService.getMonthlyPlan(id);
        return ResponseEntity.ok(monthlyPlan);
    }

    @GetMapping("/projects/{projectId}/monthly-plans")
    @Operation(summary = "프로젝트별 월별 계획 목록 조회", description = "특정 프로젝트의 모든 월별 계획을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "월별 계획 목록 조회 성공")
    @PreAuthorize("hasAnyRole('PLANNING_MANAGER', 'PLANNING_VIEWER')")
    public ResponseEntity<List<MonthlyPlanDTO>> getMonthlyPlansByProject(
            @Parameter(description = "프로젝트 ID") @PathVariable Long projectId) {
        List<MonthlyPlanDTO> monthlyPlans = planningService.getMonthlyPlansByProject(projectId);
        return ResponseEntity.ok(monthlyPlans);
    }

    @GetMapping("/projects/{projectId}/monthly-plans/year/{year}")
    @Operation(summary = "프로젝트와 연도별 월별 계획 목록 조회", description = "특정 프로젝트의 특정 연도 월별 계획을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "월별 계획 목록 조회 성공")
    @PreAuthorize("hasAnyRole('PLANNING_MANAGER', 'PLANNING_VIEWER')")
    public ResponseEntity<List<MonthlyPlanDTO>> getMonthlyPlansByProjectAndYear(
            @Parameter(description = "프로젝트 ID") @PathVariable Long projectId,
            @Parameter(description = "연도") @PathVariable Integer year) {
        List<MonthlyPlanDTO> monthlyPlans = planningService.getMonthlyPlansByProjectAndYear(projectId, year);
        return ResponseEntity.ok(monthlyPlans);
    }

    @GetMapping("/years")
    @Operation(summary = "사용 가능한 연도 목록 조회", description = "등록된 계획이 있는 모든 연도를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "연도 목록 조회 성공")
    @PreAuthorize("hasAnyRole('PLANNING_MANAGER', 'PLANNING_VIEWER')")
    public ResponseEntity<List<Integer>> getAvailableYears() {
        List<Integer> years = planningService.getAvailableYears();
        return ResponseEntity.ok(years);
    }

    @GetMapping("/grades")
    @Operation(summary = "사용 가능한 등급 목록 조회", description = "등록된 계획이 있는 모든 등급을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "등급 목록 조회 성공")
    @PreAuthorize("hasAnyRole('PLANNING_MANAGER', 'PLANNING_VIEWER')")
    public ResponseEntity<List<String>> getAvailableGrades() {
        List<String> grades = planningService.getAvailableGrades();
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/projects/{projectId}/kpi")
    @Operation(summary = "KPI 데이터 조회", description = "특정 프로젝트의 KPI 데이터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "KPI 데이터 조회 성공")
    @PreAuthorize("hasAnyRole('PLANNING_MANAGER', 'PLANNING_VIEWER')")
    public ResponseEntity<KpiData> getKpiData(
            @Parameter(description = "프로젝트 ID") @PathVariable Long projectId,
            @Parameter(description = "연도") @RequestParam Integer year,
            @Parameter(description = "월") @RequestParam Integer month) {
        KpiData kpiData = planningService.getKpiData(projectId, year, month);
        return ResponseEntity.ok(kpiData);
    }
} 