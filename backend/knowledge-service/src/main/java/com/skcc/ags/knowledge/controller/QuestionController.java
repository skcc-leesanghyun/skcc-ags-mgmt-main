package com.skcc.ags.knowledge.controller;

import com.skcc.ags.knowledge.domain.QuestionCategory;
import com.skcc.ags.knowledge.dto.QuestionDTO;
import com.skcc.ags.knowledge.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Question", description = "질문 관리 API")
@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@Validated
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "질문 생성", description = "새로운 질문을 생성합니다.")
    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(
            @Valid @RequestBody QuestionDTO questionDTO,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        QuestionDTO createdQuestion = questionService.createQuestion(questionDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @Operation(summary = "질문 수정", description = "기존 질문을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionDTO questionDTO,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO, username);
        return ResponseEntity.ok(updatedQuestion);
    }

    @Operation(summary = "질문 삭제", description = "질문을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        questionService.deleteQuestion(id, username);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "질문 조회", description = "ID로 질문을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable Long id) {
        QuestionDTO question = questionService.getQuestion(id);
        return ResponseEntity.ok(question);
    }

    @Operation(summary = "카테고리별 질문 목록 조회", description = "특정 카테고리의 질문 목록을 조회합니다.")
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<QuestionDTO>> getQuestionsByCategory(
            @PathVariable QuestionCategory category,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<QuestionDTO> questions = questionService.getQuestionsByCategory(category, pageable);
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "질문 검색", description = "키워드로 질문을 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<Page<QuestionDTO>> searchQuestions(
            @Parameter(description = "검색 키워드") @RequestParam String keyword,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<QuestionDTO> questions = questionService.searchQuestions(keyword, pageable);
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "사용자별 질문 목록 조회", description = "특정 사용자가 작성한 질문 목록을 조회합니다.")
    @GetMapping("/user")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByUser(
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        List<QuestionDTO> questions = questionService.getQuestionsByUser(username);
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "조회수 상위 질문 조회", description = "조회수가 높은 상위 N개의 질문을 조회합니다.")
    @GetMapping("/top")
    public ResponseEntity<List<QuestionDTO>> getTopQuestions(
            @Parameter(description = "조회할 질문 수") @RequestParam(defaultValue = "10") int limit) {
        List<QuestionDTO> questions = questionService.getTopQuestions(limit);
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "답변이 없는 질문 목록 조회", description = "답변이 없는 질문 목록을 조회합니다.")
    @GetMapping("/unanswered")
    public ResponseEntity<List<QuestionDTO>> getUnansweredQuestions() {
        List<QuestionDTO> questions = questionService.getUnansweredQuestions();
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "채택된 답변이 있는 질문 목록 조회", description = "채택된 답변이 있는 질문 목록을 조회합니다.")
    @GetMapping("/answered")
    public ResponseEntity<List<QuestionDTO>> getQuestionsWithAcceptedAnswers() {
        List<QuestionDTO> questions = questionService.getQuestionsWithAcceptedAnswers();
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "카테고리별 질문 수 조회", description = "특정 카테고리의 질문 수를 조회합니다.")
    @GetMapping("/count/{category}")
    public ResponseEntity<Long> getQuestionCountByCategory(@PathVariable QuestionCategory category) {
        long count = questionService.getQuestionCountByCategory(category);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "기간별 질문 목록 조회", description = "특정 기간 동안 작성된 질문 목록을 조회합니다.")
    @GetMapping("/date-range")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByDateRange(
            @Parameter(description = "시작 날짜") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "종료 날짜") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<QuestionDTO> questions = questionService.getQuestionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(questions);
    }
} 