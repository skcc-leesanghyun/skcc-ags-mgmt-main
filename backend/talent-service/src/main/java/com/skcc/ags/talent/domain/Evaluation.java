package com.skcc.ags.talent.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity representing an evaluation of a candidate.
 * Contains evaluation scores, comments, and decision details.
 */
@Entity
@Table(name = "evaluations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "evaluator_id", nullable = false)
    private String evaluatorId;

    @Column(name = "evaluator_name", nullable = false)
    private String evaluatorName;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;

    @Column(name = "technical_score")
    private Integer technicalScore;

    @Column(name = "communication_score")
    private Integer communicationScore;

    @Column(name = "problem_solving_score")
    private Integer problemSolvingScore;

    @Column(name = "team_fit_score")
    private Integer teamFitScore;

    @Column(name = "overall_score")
    private Integer overallScore;

    @ElementCollection
    @CollectionTable(
        name = "evaluation_skill_scores",
        joinColumns = @JoinColumn(name = "evaluation_id")
    )
    @MapKeyColumn(name = "skill")
    @Column(name = "score")
    @Builder.Default
    private Map<String, Integer> skillScores = new HashMap<>();

    @Column(columnDefinition = "TEXT")
    private String strengths;

    @Column(columnDefinition = "TEXT")
    private String weaknesses;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EvaluationDecision decision;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        evaluationDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Adds or updates a skill score
     * @param skill The skill being evaluated
     * @param score The score for the skill
     */
    public void setSkillScore(String skill, Integer score) {
        if (skillScores == null) {
            skillScores = new HashMap<>();
        }
        skillScores.put(skill, score);
    }

    /**
     * Removes a skill score
     * @param skill The skill to remove the score for
     */
    public void removeSkillScore(String skill) {
        if (skillScores != null) {
            skillScores.remove(skill);
        }
    }

    /**
     * Calculates the overall score based on all evaluation criteria
     * @return The calculated overall score
     */
    public double calculateOverallScore() {
        double technicalWeight = 0.4;
        double communicationWeight = 0.2;
        double problemSolvingWeight = 0.2;
        double teamFitWeight = 0.2;

        return (technicalScore * technicalWeight) +
               (communicationScore * communicationWeight) +
               (problemSolvingScore * problemSolvingWeight) +
               (teamFitScore * teamFitWeight);
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", candidate=" + (candidate != null ? candidate.getId() : null) +
                ", projectName='" + projectName + '\'' +
                ", evaluatorName='" + evaluatorName + '\'' +
                ", evaluationDate=" + evaluationDate +
                ", overallScore=" + overallScore +
                ", decision=" + decision +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evaluation)) return false;
        Evaluation that = (Evaluation) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
} 