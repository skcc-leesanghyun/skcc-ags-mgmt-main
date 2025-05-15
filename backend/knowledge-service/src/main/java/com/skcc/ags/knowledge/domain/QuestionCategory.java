package com.skcc.ags.knowledge.domain;

public enum QuestionCategory {
    GENERAL("일반"),
    TECHNICAL("기술"),
    PROCESS("프로세스"),
    SECURITY("보안"),
    SYSTEM("시스템"),
    OTHER("기타");

    private final String displayName;

    QuestionCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 