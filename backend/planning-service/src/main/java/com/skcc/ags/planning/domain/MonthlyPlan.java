package com.skcc.ags.planning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monthly_plans")
@Getter
@Setter
public class MonthlyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "project_id")
    private Long projectId;

    @NotNull
    @Min(2000)
    @Column(name = "plan_year")
    private Integer planYear;

    @NotNull
    @Min(1)
    @Column(name = "plan_month")
    private Integer planMonth;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "monthlyPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradePlan> gradePlans = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addGradePlan(GradePlan gradePlan) {
        gradePlans.add(gradePlan);
        gradePlan.setMonthlyPlan(this);
    }

    public void removeGradePlan(GradePlan gradePlan) {
        gradePlans.remove(gradePlan);
        gradePlan.setMonthlyPlan(null);
    }
} 