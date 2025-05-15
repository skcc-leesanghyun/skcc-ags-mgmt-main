package com.skcc.ags.talent.domain;

/**
 * Enum representing different types of candidate evaluations in the AGS talent sourcing system.
 */
public enum EvaluationType {
    /**
     * Initial screening evaluation of candidate's basic qualifications
     */
    INITIAL_SCREENING("Initial Screening", "기초 심사"),

    /**
     * Technical interview to assess candidate's technical skills
     */
    TECHNICAL_INTERVIEW("Technical Interview", "기술 면접"),

    /**
     * Project fit assessment to evaluate candidate's suitability for specific project
     */
    PROJECT_FIT("Project Fit Assessment", "프로젝트 적합성 평가"),

    /**
     * Client interview with the end client
     */
    CLIENT_INTERVIEW("Client Interview", "고객사 면접"),

    /**
     * Final comprehensive evaluation
     */
    FINAL_EVALUATION("Final Evaluation", "최종 평가");

    private final String englishName;
    private final String koreanName;

    EvaluationType(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    /**
     * Get the localized name based on the language parameter
     * @param isKorean true for Korean, false for English
     * @return The localized name of the evaluation type
     */
    public String getLocalizedName(boolean isKorean) {
        return isKorean ? koreanName : englishName;
    }
} 