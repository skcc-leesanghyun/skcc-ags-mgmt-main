package com.skcc.ags.talent.domain;

/**
 * Enum representing different types of documents that can be associated with a candidate.
 */
public enum CandidateDocumentType {
    /**
     * Candidate's resume/CV
     */
    RESUME("Resume", "이력서", "후보자의 경력과 기술을 상세히 기술한 이력서", true),

    /**
     * Portfolio of previous work
     */
    PORTFOLIO("Portfolio", "포트폴리오", "과거 프로젝트 및 작업물 모음", false),

    /**
     * Academic certificates and degrees
     */
    ACADEMIC_CERTIFICATE("Academic Certificate", "학위/졸업증명서", "학위 및 교육 이수 증명서", true),

    /**
     * Professional certifications
     */
    PROFESSIONAL_CERTIFICATION("Professional Certification", "자격증", "전문 자격증 및 인증서", false),

    /**
     * Previous employment certificates
     */
    EMPLOYMENT_CERTIFICATE("Employment Certificate", "경력증명서", "이전 직장 경력 증명서", true),

    /**
     * Technical skill assessment results
     */
    SKILL_ASSESSMENT("Skill Assessment", "기술평가결과", "기술 역량 평가 결과서", false),

    /**
     * Interview evaluation documents
     */
    INTERVIEW_EVALUATION("Interview Evaluation", "면접평가서", "면접 평가 결과 문서", true),

    /**
     * Reference letters from previous employers
     */
    REFERENCE_LETTER("Reference Letter", "추천서", "이전 고용주/관리자의 추천서", false),

    /**
     * Background check results
     */
    BACKGROUND_CHECK("Background Check", "신원조회결과", "신원 조회 및 확인 결과", true),

    /**
     * Non-disclosure agreement
     */
    NDA("Non-Disclosure Agreement", "기밀유지서약서", "기밀 유지 관련 서약서", true),

    /**
     * Work visa or permit documents
     */
    WORK_PERMIT("Work Permit", "취업허가서", "취업 비자 또는 허가 관련 서류", true),

    /**
     * Other supplementary documents
     */
    OTHER("Other", "기타", "기타 보충 서류", false);

    private final String englishName;
    private final String koreanName;
    private final String description;
    private final boolean required;

    CandidateDocumentType(String englishName, String koreanName, String description, boolean required) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.description = description;
        this.required = required;
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

    public boolean isRequired() {
        return required;
    }

    /**
     * Get the localized name based on the language parameter
     * @param isKorean true for Korean, false for English
     * @return The localized name of the document type
     */
    public String getLocalizedName(boolean isKorean) {
        return isKorean ? koreanName : englishName;
    }

    /**
     * Check if the document type requires verification
     * @return true if the document needs verification
     */
    public boolean requiresVerification() {
        return this == ACADEMIC_CERTIFICATE || 
               this == PROFESSIONAL_CERTIFICATION || 
               this == EMPLOYMENT_CERTIFICATE ||
               this == WORK_PERMIT;
    }

    /**
     * Check if the document type has an expiration date
     * @return true if the document can expire
     */
    public boolean hasExpirationDate() {
        return this == PROFESSIONAL_CERTIFICATION || 
               this == WORK_PERMIT || 
               this == NDA;
    }

    /**
     * Get the maximum allowed file size in MB for this document type
     * @return the maximum file size in MB
     */
    public int getMaxFileSizeMB() {
        switch (this) {
            case PORTFOLIO:
                return 50;  // Portfolios can be larger
            case RESUME:
            case ACADEMIC_CERTIFICATE:
            case PROFESSIONAL_CERTIFICATION:
            case EMPLOYMENT_CERTIFICATE:
            case WORK_PERMIT:
                return 10;  // Standard documents
            default:
                return 20;  // Other documents
        }
    }

    /**
     * Get allowed file extensions for this document type
     * @return array of allowed file extensions
     */
    public String[] getAllowedFileExtensions() {
        switch (this) {
            case RESUME:
            case PORTFOLIO:
                return new String[] {".pdf", ".doc", ".docx", ".ppt", ".pptx"};
            case ACADEMIC_CERTIFICATE:
            case PROFESSIONAL_CERTIFICATION:
            case EMPLOYMENT_CERTIFICATE:
            case WORK_PERMIT:
                return new String[] {".pdf", ".jpg", ".jpeg", ".png"};
            default:
                return new String[] {".pdf"};
        }
    }
} 