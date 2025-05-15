package com.skcc.ags.talent.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a candidate in the AGS talent sourcing system.
 * Contains candidate details, skills, experience, and evaluation history.
 */
@Entity
@Table(name = "candidates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "english_name")
    private String englishName;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "partner_company", nullable = false)
    private String partnerCompany;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "technical_grade")
    private String technicalGrade;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column(name = "availability_date")
    private LocalDateTime availabilityDate;

    @Column(name = "current_location")
    private String currentLocation;

    @Column(name = "preferred_location")
    private String preferredLocation;

    @Column(name = "notice_period")
    private Integer noticePeriod;

    @Column(name = "expected_salary")
    private Double expectedSalary;

    @Column(name = "resume_url")
    private String resumeUrl;

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

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidateFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Adds an evaluation to the candidate
     * @param evaluation The evaluation to add
     */
    public void addEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
        evaluation.setCandidate(this);
    }

    /**
     * Removes an evaluation from the candidate
     * @param evaluation The evaluation to remove
     */
    public void removeEvaluation(Evaluation evaluation) {
        evaluations.remove(evaluation);
        evaluation.setCandidate(null);
    }

    /**
     * Adds a skill to the candidate's skill list
     * @param skill The skill to add
     */
    public void addSkill(String skill) {
        if (skills == null) {
            skills = "";
        }
        skills += skill + "\n";
    }

    /**
     * Removes a skill from the candidate's skill list
     * @param skill The skill to remove
     */
    public void removeSkill(String skill) {
        if (skills != null) {
            skills = skills.replace(skill + "\n", "").trim();
        }
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", partnerCompany='" + partnerCompany + '\'' +
                ", technicalGrade='" + technicalGrade + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidate)) return false;
        Candidate that = (Candidate) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
} 