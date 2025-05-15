package com.skcc.ags.planning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "grade_plans")
@Getter
@Setter
public class GradePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthly_plan_id")
    private MonthlyPlan monthlyPlan;

    @NotNull
    @Column(name = "grade")
    private String grade;

    @NotNull
    @Min(0)
    @Column(name = "planned_count")
    private Integer plannedCount;

    @Min(0)
    @Column(name = "actual_count")
    private Integer actualCount;
} 