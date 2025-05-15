package com.skcc.ags.talent.domain;

/**
 * Enum representing different states of a proposal in the talent sourcing system
 */
public enum ProposalStatus {
    /**
     * Initial draft state
     */
    DRAFT("Draft", "임시저장", "제안서 초안 작성 중"),

    /**
     * Proposal is submitted and waiting for review
     */
    SUBMITTED("Submitted", "제출완료", "검토 대기 중인 제안서"),

    /**
     * Proposal is under review by the project team
     */
    IN_REVIEW("In Review", "검토중", "프로젝트 팀이 검토 중인 제안서"),

    /**
     * Proposal needs revision based on feedback
     */
    NEEDS_REVISION("Needs Revision", "수정필요", "피드백을 반영한 수정이 필요한 제안서"),

    /**
     * Proposal is approved and candidate is being considered
     */
    APPROVED("Approved", "승인", "승인된 제안서, 후보자 고려 중"),

    /**
     * Proposal is shortlisted for final consideration
     */
    SHORTLISTED("Shortlisted", "후보선정", "최종 고려 대상으로 선정된 제안서"),

    /**
     * Proposal is accepted and candidate is selected
     */
    ACCEPTED("Accepted", "채택", "채택된 제안서, 후보자 선정 완료"),

    /**
     * Proposal is rejected
     */
    REJECTED("Rejected", "반려", "반려된 제안서"),

    /**
     * Proposal is withdrawn by the partner company
     */
    WITHDRAWN("Withdrawn", "철회", "협력사가 철회한 제안서"),

    /**
     * Proposal is expired due to timeline or other constraints
     */
    EXPIRED("Expired", "만료", "기한 또는 기타 제약으로 만료된 제안서");

    private final String englishName;
    private final String koreanName;
    private final String description;

    ProposalStatus(String englishName, String koreanName, String description) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.description = description;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get the localized name based on the language parameter
     * @param isKorean true for Korean, false for English
     * @return The localized name of the status
     */
    public String getLocalizedName(boolean isKorean) {
        return isKorean ? koreanName : englishName;
    }

    /**
     * Check if the status is a final state
     * @return true if the status is final (ACCEPTED, REJECTED, WITHDRAWN, or EXPIRED)
     */
    public boolean isFinal() {
        return this == ACCEPTED || this == REJECTED || 
               this == WITHDRAWN || this == EXPIRED;
    }

    /**
     * Check if the status is active (not final)
     * @return true if the status is not final
     */
    public boolean isActive() {
        return !isFinal();
    }

    /**
     * Check if the status allows feedback
     * @return true if feedback can be provided in this status
     */
    public boolean allowsFeedback() {
        return this == IN_REVIEW || this == NEEDS_REVISION || 
               this == APPROVED || this == SHORTLISTED || 
               this == ACCEPTED || this == REJECTED;
    }

    /**
     * Check if the status allows editing
     * @return true if the proposal can be edited in this status
     */
    public boolean allowsEditing() {
        return this == DRAFT || this == NEEDS_REVISION;
    }

    /**
     * Check if the status requires review
     * @return true if the proposal needs review in this status
     */
    public boolean requiresReview() {
        return this == SUBMITTED || this == IN_REVIEW;
    }
} 