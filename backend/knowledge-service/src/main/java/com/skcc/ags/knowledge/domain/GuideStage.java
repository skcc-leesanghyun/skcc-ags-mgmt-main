package com.skcc.ags.knowledge.domain;

public enum GuideStage {
    PLANNING("기획"),
    DEVELOPMENT("개발"),
    TESTING("테스트"),
    DEPLOYMENT("배포"),
    OPERATION("운영"),
    MAINTENANCE("유지보수");

    private final String displayName;

    GuideStage(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 