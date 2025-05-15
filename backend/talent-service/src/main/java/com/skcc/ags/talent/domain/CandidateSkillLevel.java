package com.skcc.ags.talent.domain;

/**
 * Enum representing different skill levels for candidates in the AGS talent sourcing system.
 */
public enum CandidateSkillLevel {
    /**
     * Expert level with comprehensive knowledge and experience
     */
    EXPERT("Expert", "전문가", "해당 기술의 전문가 수준으로, 복잡한 문제 해결 및 아키텍처 설계 가능", 5),

    /**
     * Advanced level with deep understanding and practical experience
     */
    ADVANCED("Advanced", "고급", "심화된 지식과 실무 경험을 보유하여 독립적으로 업무 수행 가능", 4),

    /**
     * Intermediate level with good working knowledge
     */
    INTERMEDIATE("Intermediate", "중급", "기본적인 실무 능력을 갖추고 일반적인 업무 수행 가능", 3),

    /**
     * Basic level with fundamental understanding
     */
    BASIC("Basic", "초급", "기본적인 이해도를 갖추고 있으며 지원을 받아 업무 수행 가능", 2),

    /**
     * Beginner level with minimal experience
     */
    BEGINNER("Beginner", "입문", "기초적인 지식을 보유하고 있으나 실무 경험이 부족", 1),

    /**
     * Theoretical knowledge only, no practical experience
     */
    THEORETICAL("Theoretical", "이론", "이론적 지식만 보유하고 있으며 실무 경험 없음", 0);

    private final String englishName;
    private final String koreanName;
    private final String description;
    private final int skillScore;

    CandidateSkillLevel(String englishName, String koreanName, String description, int skillScore) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.description = description;
        this.skillScore = skillScore;
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

    public int getSkillScore() {
        return skillScore;
    }

    /**
     * Get the localized name based on the language parameter
     * @param isKorean true for Korean, false for English
     * @return The localized name of the skill level
     */
    public String getLocalizedName(boolean isKorean) {
        return isKorean ? koreanName : englishName;
    }

    /**
     * Check if the skill level is considered senior level
     * @return true if the skill level is Advanced or Expert
     */
    public boolean isSeniorLevel() {
        return this == ADVANCED || this == EXPERT;
    }

    /**
     * Check if the skill level meets the minimum requirement
     * @param minimumLevel the minimum required level
     * @return true if this level is equal to or higher than the minimum required level
     */
    public boolean meetsMinimumRequirement(CandidateSkillLevel minimumLevel) {
        return this.skillScore >= minimumLevel.skillScore;
    }

    /**
     * Get years of experience typically associated with this skill level
     * @return the typical years of experience for this level
     */
    public int getTypicalYearsOfExperience() {
        switch (this) {
            case EXPERT:
                return 8;
            case ADVANCED:
                return 5;
            case INTERMEDIATE:
                return 3;
            case BASIC:
                return 1;
            case BEGINNER:
            case THEORETICAL:
            default:
                return 0;
        }
    }
} 