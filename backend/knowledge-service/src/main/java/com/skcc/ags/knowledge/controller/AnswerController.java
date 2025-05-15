package com.skcc.ags.knowledge.controller;

import com.skcc.ags.knowledge.dto.AnswerDTO;
import com.skcc.ags.knowledge.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Answer", description = "답변 관리 API")
@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
@Validated
public class AnswerController {

    private final AnswerService answerService;

    @Operation(summary = "답변 생성", description = "새로운 답변을 생성합니다.")
    @PostMapping("/question/{questionId}")
    public ResponseEntity<AnswerDTO> createAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerDTO answerDTO,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        AnswerDTO createdAnswer = answerService.createAnswer(questionId, answerDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswer);
    }

    @Operation(summary = "답변 수정", description = "기존 답변을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<AnswerDTO> updateAnswer(
            @PathVariable Long id,
            @Valid @RequestBody AnswerDTO answerDTO,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        AnswerDTO updatedAnswer = answerService.updateAnswer(id, answerDTO, username);
        return ResponseEntity.ok(updatedAnswer);
    }

    @Operation(summary = "답변 삭제", description = "답변을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        answerService.deleteAnswer(id, username);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "답변 조회", description = "ID로 답변을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable Long id) {
        AnswerDTO answer = answerService.getAnswer(id);
        return ResponseEntity.ok(answer);
    }

    @Operation(summary = "질문별 답변 목록 조회", description = "특정 질문의 답변 목록을 조회합니다.")
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerDTO>> getAnswersByQuestion(@PathVariable Long questionId) {
        List<AnswerDTO> answers = answerService.getAnswersByQuestion(questionId);
        return ResponseEntity.ok(answers);
    }

    @Operation(summary = "사용자별 답변 목록 조회", description = "특정 사용자가 작성한 답변 목록을 조회합니다.")
    @GetMapping("/user")
    public ResponseEntity<List<AnswerDTO>> getAnswersByUser(
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        List<AnswerDTO> answers = answerService.getAnswersByUser(username);
        return ResponseEntity.ok(answers);
    }

    @Operation(summary = "답변 채택", description = "답변을 채택합니다.")
    @PutMapping("/{id}/accept")
    public ResponseEntity<AnswerDTO> acceptAnswer(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        AnswerDTO acceptedAnswer = answerService.acceptAnswer(id, username);
        return ResponseEntity.ok(acceptedAnswer);
    }

    @Operation(summary = "답변 채택 취소", description = "답변 채택을 취소합니다.")
    @PutMapping("/{id}/unaccept")
    public ResponseEntity<AnswerDTO> unacceptAnswer(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        AnswerDTO unacceptedAnswer = answerService.unacceptAnswer(id, username);
        return ResponseEntity.ok(unacceptedAnswer);
    }

    @Operation(summary = "답변 추천", description = "답변을 추천합니다.")
    @PostMapping("/{id}/upvote")
    public ResponseEntity<Void> upvoteAnswer(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        answerService.upvoteAnswer(id, username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "답변 추천 취소", description = "답변 추천을 취소합니다.")
    @DeleteMapping("/{id}/upvote")
    public ResponseEntity<Void> removeUpvote(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        answerService.removeUpvote(id, username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "답변 비추천", description = "답변을 비추천합니다.")
    @PostMapping("/{id}/downvote")
    public ResponseEntity<Void> downvoteAnswer(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        answerService.downvoteAnswer(id, username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "답변 비추천 취소", description = "답변 비추천을 취소합니다.")
    @DeleteMapping("/{id}/downvote")
    public ResponseEntity<Void> removeDownvote(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        answerService.removeDownvote(id, username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "최근 답변 목록 조회", description = "최근에 작성된 답변 목록을 조회합니다.")
    @GetMapping("/recent")
    public ResponseEntity<Page<AnswerDTO>> getRecentAnswers(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<AnswerDTO> answers = answerService.getRecentAnswers(pageable);
        return ResponseEntity.ok(answers);
    }

    @Operation(summary = "추천수 상위 답변 조회", description = "추천수가 높은 상위 N개의 답변을 조회합니다.")
    @GetMapping("/top")
    public ResponseEntity<List<AnswerDTO>> getTopVotedAnswers(
            @Parameter(description = "조회할 답변 수") @RequestParam(defaultValue = "10") int limit) {
        List<AnswerDTO> answers = answerService.getTopVotedAnswers(limit);
        return ResponseEntity.ok(answers);
    }

    @Operation(summary = "사용자별 채택된 답변 수 조회", description = "특정 사용자의 채택된 답변 수를 조회합니다.")
    @GetMapping("/user/accepted/count")
    public ResponseEntity<Long> getAcceptedAnswerCount(
            @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("preferred_username");
        long count = answerService.countAcceptedAnswersByUser(username);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "질문별 답변 수 조회", description = "특정 질문의 답변 수를 조회합니다.")
    @GetMapping("/question/{questionId}/count")
    public ResponseEntity<Long> getAnswerCountByQuestion(@PathVariable Long questionId) {
        long count = answerService.countAnswersByQuestion(questionId);
        return ResponseEntity.ok(count);
    }
} 