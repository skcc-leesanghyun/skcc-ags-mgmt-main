package com.skcc.ags.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skcc.ags.knowledge.domain.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 투표 정보를 전송하기 위한 DTO 클래스
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {

    /**
     * 투표의 고유 식별자
     */
    private Long id;

    /**
     * 투표가 달린 답변의 ID
     */
    @NotNull(message = "답변 ID는 필수입니다")
    private Long answerId;

    /**
     * 투표한 사용자의 사용자명
     */
    private String username;

    /**
     * 투표 유형 (추천/비추천)
     */
    @NotNull(message = "투표 유형은 필수입니다")
    private VoteType type;

    /**
     * 투표 생성 일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 투표 요약 정보를 문자열로 반환
     */
    @Override
    public String toString() {
        return "VoteDTO{" +
                "id=" + id +
                ", answerId=" + answerId +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
} 