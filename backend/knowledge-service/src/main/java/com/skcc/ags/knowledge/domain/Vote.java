package com.skcc.ags.knowledge.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 답변에 대한 투표를 나타내는 엔티티 클래스
 */
@Entity
@Table(name = "votes",
       uniqueConstraints = {
           @UniqueConstraint(
               name = "uk_votes_answer_username_type",
               columnNames = {"answer_id", "username", "vote_type"}
           )
       })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    /**
     * 투표의 고유 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 투표가 달린 답변
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    /**
     * 투표한 사용자의 사용자명
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 투표 유형 (추천/비추천)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType type;

    /**
     * 투표 생성 일시
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 투표 정보를 문자열로 반환
     */
    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", answerId=" + (answer != null ? answer.getId() : null) +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }

    /**
     * 동일성 비교
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return id != null && id.equals(vote.getId());
    }

    /**
     * 해시코드 생성
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
} 