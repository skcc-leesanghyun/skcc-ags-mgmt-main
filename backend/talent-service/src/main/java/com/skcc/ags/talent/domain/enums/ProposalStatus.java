package com.skcc.ags.talent.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration of possible proposal statuses.
 */
@Getter
@RequiredArgsConstructor
public enum ProposalStatus {
    
    DRAFT("초안", "제안서 작성 중"),
    SUBMITTED("제출됨", "검토 대기 중"),
    UNDER_REVIEW("검토중", "평가자 검토 진행 중"),
    REVISION_REQUESTED("수정요청", "수정 사항 반영 필요"),
    REVISED("수정완료", "수정 사항 반영 완료"),
    APPROVED("승인", "제안서 승인 완료"),
    REJECTED("반려", "제안서 반려"),
    EXPIRED("만료", "검토 기한 만료"),
    WITHDRAWN("철회", "제안사 철회"),
    ARCHIVED("보관", "제안 프로세스 종료");

    private final String koreanName;
    private final String description;

    /**
     * Check if the status can be transitioned to the target status.
     *
     * @param targetStatus the status to transition to
     * @return true if the transition is allowed
     */
    public boolean canTransitionTo(ProposalStatus targetStatus) {
        switch (this) {
            case DRAFT:
                return targetStatus == SUBMITTED || targetStatus == WITHDRAWN;
            case SUBMITTED:
                return targetStatus == UNDER_REVIEW || targetStatus == WITHDRAWN || targetStatus == EXPIRED;
            case UNDER_REVIEW:
                return targetStatus == APPROVED || targetStatus == REJECTED || targetStatus == REVISION_REQUESTED;
            case REVISION_REQUESTED:
                return targetStatus == REVISED || targetStatus == WITHDRAWN;
            case REVISED:
                return targetStatus == UNDER_REVIEW;
            case APPROVED:
                return targetStatus == ARCHIVED;
            case REJECTED:
                return targetStatus == ARCHIVED;
            case EXPIRED:
                return targetStatus == ARCHIVED;
            case WITHDRAWN:
                return targetStatus == ARCHIVED;
            case ARCHIVED:
                return false;
            default:
                return false;
        }
    }

    /**
     * Check if the status is a final status.
     *
     * @return true if the status is final
     */
    public boolean isFinalStatus() {
        return this == APPROVED || this == REJECTED || this == EXPIRED || 
               this == WITHDRAWN || this == ARCHIVED;
    }

    /**
     * Check if the status is active (not final).
     *
     * @return true if the status is active
     */
    public boolean isActive() {
        return !isFinalStatus();
    }

    /**
     * Check if the status allows feedback submission.
     *
     * @return true if feedback can be submitted
     */
    public boolean allowsFeedback() {
        return this == UNDER_REVIEW;
    }

    /**
     * Check if the status allows modifications.
     *
     * @return true if modifications are allowed
     */
    public boolean allowsModification() {
        return this == DRAFT || this == REVISION_REQUESTED;
    }

    /**
     * Get the next possible statuses from the current status.
     *
     * @return array of possible next statuses
     */
    public ProposalStatus[] getNextPossibleStatuses() {
        switch (this) {
            case DRAFT:
                return new ProposalStatus[]{SUBMITTED, WITHDRAWN};
            case SUBMITTED:
                return new ProposalStatus[]{UNDER_REVIEW, WITHDRAWN, EXPIRED};
            case UNDER_REVIEW:
                return new ProposalStatus[]{APPROVED, REJECTED, REVISION_REQUESTED};
            case REVISION_REQUESTED:
                return new ProposalStatus[]{REVISED, WITHDRAWN};
            case REVISED:
                return new ProposalStatus[]{UNDER_REVIEW};
            case APPROVED:
            case REJECTED:
            case EXPIRED:
            case WITHDRAWN:
                return new ProposalStatus[]{ARCHIVED};
            case ARCHIVED:
                return new ProposalStatus[]{};
            default:
                return new ProposalStatus[]{};
        }
    }
} 