package com.skcc.ags.talent.domain;

/**
 * Enum representing the various states a candidate can be in during the recruitment process.
 */
public enum CandidateStatus {
    /**
     * Initial state when candidate is first registered in the system
     */
    NEW("New", "신규 등록", "후보자가 시스템에 처음 등록된 상태"),

    /**
     * Candidate's profile is under initial review
     */
    SCREENING("Under Screening", "서류 심사 중", "후보자의 기본 자격과 프로필을 검토 중인 상태"),

    /**
     * Candidate has passed initial screening and is ready for evaluation
     */
    READY_FOR_EVALUATION("Ready for Evaluation", "평가 대기", "서류 심사를 통과하여 평가 진행이 가능한 상태"),

    /**
     * Candidate is currently being evaluated
     */
    IN_EVALUATION("In Evaluation", "평가 진행 중", "기술 면접 등 평가가 진행 중인 상태"),

    /**
     * Candidate has been shortlisted for specific project
     */
    SHORTLISTED("Shortlisted", "후보 선정", "특정 프로젝트의 후보자로 선정된 상태"),

    /**
     * Candidate is being presented to client
     */
    CLIENT_REVIEW("Client Review", "고객사 검토 중", "고객사에서 후보자를 검토 중인 상태"),

    /**
     * Candidate has been selected and offer is being prepared
     */
    SELECTED("Selected", "최종 선발", "프로젝트에 최종 선발된 상태"),

    /**
     * Offer has been made to the candidate
     */
    OFFER_MADE("Offer Made", "오퍼 진행 중", "후보자에게 오퍼를 제시한 상태"),

    /**
     * Candidate has accepted the offer
     */
    OFFER_ACCEPTED("Offer Accepted", "오퍼 수락", "후보자가 오퍼를 수락한 상태"),

    /**
     * Candidate has declined the offer
     */
    OFFER_DECLINED("Offer Declined", "오퍼 거절", "후보자가 오퍼를 거절한 상태"),

    /**
     * Candidate has been rejected
     */
    REJECTED("Rejected", "불합격", "평가 과정에서 부적합 판정을 받은 상태"),

    /**
     * Candidate has withdrawn from the process
     */
    WITHDRAWN("Withdrawn", "지원 철회", "후보자가 채용 프로세스에서 자진 철회한 상태"),

    /**
     * Candidate's profile is on hold
     */
    ON_HOLD("On Hold", "보류", "추가 검토나 future opportunity를 위해 보류된 상태"),

    /**
     * Candidate has joined the project
     */
    JOINED("Joined", "프로젝트 참여", "프로젝트에 최종 합류한 상태");

    private final String englishName;
    private final String koreanName;
    private final String description;

    CandidateStatus(String englishName, String koreanName, String description) {
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
     * @return The localized name of the candidate status
     */
    public String getLocalizedName(boolean isKorean) {
        return isKorean ? koreanName : englishName;
    }

    /**
     * Check if the status is an active status (candidate is still in consideration)
     * @return true if the status is active, false otherwise
     */
    public boolean isActive() {
        return this != REJECTED && this != WITHDRAWN && this != OFFER_DECLINED;
    }

    /**
     * Check if the status is a final status (no further action needed)
     * @return true if the status is final, false otherwise
     */
    public boolean isFinal() {
        return this == JOINED || this == REJECTED || this == WITHDRAWN || this == OFFER_DECLINED;
    }

    /**
     * Check if the candidate can be evaluated in current status
     * @return true if evaluation is possible, false otherwise
     */
    public boolean canBeEvaluated() {
        return this == READY_FOR_EVALUATION || this == IN_EVALUATION || this == SHORTLISTED;
    }
} 