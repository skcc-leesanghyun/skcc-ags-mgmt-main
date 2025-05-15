package com.skcc.ags.knowledge.controller;

import com.skcc.ags.knowledge.domain.VoteType;
import com.skcc.ags.knowledge.dto.VoteDTO;
import com.skcc.ags.knowledge.repository.VoteRepository.VoteStats;
import com.skcc.ags.knowledge.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 투표 관련 HTTP 요청을 처리하는 REST 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
@Tag(name = "Vote", description = "투표 관리 API")
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "답변에 대한 투표 생성", description = "특정 답변에 대해 추천/비추천 투표를 생성합니다.")
    @PostMapping("/answer/{answerId}")
    public ResponseEntity<VoteDTO> createVote(
            @Parameter(description = "답변 ID") @PathVariable Long answerId,
            @Parameter(description = "투표 유형") @RequestParam VoteType type,
            @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        String username = oauth2User.getAttribute("preferred_username");
        VoteDTO vote = voteService.createVote(answerId, type, username);
        return ResponseEntity.ok(vote);
    }

    @Operation(summary = "투표 삭제", description = "특정 답변에 대한 사용자의 투표를 삭제합니다.")
    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<Void> deleteVote(
            @Parameter(description = "답변 ID") @PathVariable Long answerId,
            @Parameter(description = "투표 유형") @RequestParam VoteType type,
            @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        String username = oauth2User.getAttribute("preferred_username");
        voteService.deleteVote(answerId, type, username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "답변의 투표 통계 조회", description = "특정 답변의 투표 통계를 조회합니다.")
    @GetMapping("/stats/answer/{answerId}")
    public ResponseEntity<List<VoteStats>> getVoteStats(
            @Parameter(description = "답변 ID") @PathVariable Long answerId
    ) {
        return ResponseEntity.ok(voteService.getVoteStats(answerId));
    }

    @Operation(summary = "사용자의 투표 여부 확인", description = "특정 답변에 대한 사용자의 투표 여부를 확인합니다.")
    @GetMapping("/check/answer/{answerId}")
    public ResponseEntity<Boolean> hasUserVoted(
            @Parameter(description = "답변 ID") @PathVariable Long answerId,
            @Parameter(description = "투표 유형") @RequestParam VoteType type,
            @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        String username = oauth2User.getAttribute("preferred_username");
        return ResponseEntity.ok(voteService.hasUserVoted(answerId, username, type));
    }

    @Operation(summary = "기간별 투표 통계 조회", description = "특정 기간 동안의 투표 통계를 조회합니다.")
    @GetMapping("/stats/date-range")
    public ResponseEntity<Map<VoteType, Long>> getVoteStatsByDateRange(
            @Parameter(description = "시작 일시") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "종료 일시") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return ResponseEntity.ok(voteService.getVoteStatsByDateRange(startDate, endDate));
    }

    @Operation(summary = "사용자의 투표 이력 조회", description = "현재 사용자의 모든 투표 이력을 조회합니다.")
    @GetMapping("/history")
    public ResponseEntity<List<VoteDTO>> getUserVoteHistory(
            @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        String username = oauth2User.getAttribute("preferred_username");
        return ResponseEntity.ok(voteService.getUserVoteHistory(username));
    }

    @Operation(summary = "답변의 투표 목록 조회", description = "특정 답변에 대한 모든 투표를 조회합니다.")
    @GetMapping("/answer/{answerId}/list")
    public ResponseEntity<List<VoteDTO>> getVotesByAnswer(
            @Parameter(description = "답변 ID") @PathVariable Long answerId
    ) {
        return ResponseEntity.ok(voteService.getVotesByAnswer(answerId));
    }

    @Operation(summary = "사용자의 투표 통계 조회", description = "현재 사용자의 투표 통계를 조회합니다.")
    @GetMapping("/stats/user")
    public ResponseEntity<Map<VoteType, Long>> getUserVoteStats(
            @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        String username = oauth2User.getAttribute("preferred_username");
        return ResponseEntity.ok(voteService.getUserVoteStats(username));
    }

    @Operation(summary = "답변의 투표 요약 조회", description = "특정 답변의 투표 요약 정보를 조회합니다.")
    @GetMapping("/summary/answer/{answerId}")
    public ResponseEntity<Map<String, Long>> getVoteSummary(
            @Parameter(description = "답변 ID") @PathVariable Long answerId
    ) {
        return ResponseEntity.ok(voteService.getVoteSummary(answerId));
    }

    @Operation(summary = "사용자가 투표한 답변 ID 목록 조회", description = "현재 사용자가 투표한 답변들의 ID 목록을 조회합니다.")
    @GetMapping("/voted-answers")
    public ResponseEntity<List<Long>> getVotedAnswerIds(
            @Parameter(description = "투표 유형") @RequestParam VoteType type,
            @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        String username = oauth2User.getAttribute("preferred_username");
        return ResponseEntity.ok(voteService.getVotedAnswerIds(username, type));
    }
} 