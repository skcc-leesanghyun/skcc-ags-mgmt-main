package com.skcc.ags.knowledge.domain;

public enum LessonsLearnedCategory {
    PROJECT_PLANNING("프로젝트 계획"),
    RESOURCE_MANAGEMENT("리소스 관리"),
    RISK_MANAGEMENT("리스크 관리"),
    COMMUNICATION("의사소통"),
    TECHNICAL("기술적 이슈"),
    PROCESS_IMPROVEMENT("프로세스 개선"),
    BEST_PRACTICE("모범 사례"),
    GENERAL("일반");

    private final String description;

    LessonsLearnedCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
} 