package com.skcc.ags.planning.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a manpower plan for a project.
 * This class tracks planned and actual manpower usage for specific time periods.
 */
@Entity
@Table(name = "manpower_plans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManpowerPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private LocalDate planDate;

    @Column(nullable = false)
    private Integer plannedManpower;

    @Column
    private Integer actualManpower;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private String lastModifiedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Updates the actual manpower value for this plan.
     *
     * @param actualManpower The actual manpower value to set
     */
    public void updateActualManpower(Integer actualManpower) {
        this.actualManpower = actualManpower;
    }

    /**
     * Calculates the variance between planned and actual manpower.
     * A positive value indicates actual manpower exceeded the plan.
     * A negative value indicates actual manpower was less than planned.
     *
     * @return The difference between actual and planned manpower, or null if actual manpower is not yet recorded
     */
    public Integer calculateVariance() {
        if (actualManpower == null) {
            return null;
        }
        return actualManpower - plannedManpower;
    }

    /**
     * Checks if this plan has actual manpower data recorded.
     *
     * @return true if actual manpower data exists, false otherwise
     */
    public boolean hasActualData() {
        return actualManpower != null;
    }

    @Override
    public String toString() {
        return "ManpowerPlan{" +
                "id=" + id +
                ", project=" + (project != null ? project.getId() : null) +
                ", planDate=" + planDate +
                ", plannedManpower=" + plannedManpower +
                ", actualManpower=" + actualManpower +
                ", notes='" + notes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManpowerPlan)) return false;
        ManpowerPlan that = (ManpowerPlan) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
} 