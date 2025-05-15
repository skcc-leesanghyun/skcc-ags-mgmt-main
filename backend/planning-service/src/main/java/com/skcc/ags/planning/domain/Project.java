package com.skcc.ags.planning.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a project in the AGS system.
 * Contains project details, manpower requirements, and associated plans.
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "project_code", nullable = false, unique = true)
    private String projectCode;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "project_manager")
    private String projectManager;

    @Column(name = "business_unit")
    private String businessUnit;

    @Column(name = "partner_company")
    private String partnerCompany;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @Column(name = "total_manpower")
    private Integer totalManpower;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ManpowerPlan> manpowerPlans = new ArrayList<>();

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

    /**
     * Adds a manpower plan to the project
     * @param plan The manpower plan to add
     */
    public void addManpowerPlan(ManpowerPlan plan) {
        manpowerPlans.add(plan);
        plan.setProject(this);
    }

    /**
     * Removes a manpower plan from the project
     * @param plan The manpower plan to remove
     */
    public void removeManpowerPlan(ManpowerPlan plan) {
        manpowerPlans.remove(plan);
        plan.setProject(null);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", status=" + status +
                ", totalManpower=" + totalManpower +
                '}';
    }
} 