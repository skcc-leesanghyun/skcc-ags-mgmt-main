package com.skcc.ags.knowledge.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lessons_learned")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonsLearned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonsLearnedCategory category;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LessonsLearned(String title, String content, Long projectId, String projectName,
                         LessonsLearnedCategory category, String createdBy) {
        this.title = title;
        this.content = content;
        this.projectId = projectId;
        this.projectName = projectName;
        this.category = category;
        this.viewCount = 0L;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String content, Long projectId, String projectName,
                      LessonsLearnedCategory category, String updatedBy) {
        this.title = title;
        this.content = content;
        this.projectId = projectId;
        this.projectName = projectName;
        this.category = category;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
} 