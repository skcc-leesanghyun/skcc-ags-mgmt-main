package com.skcc.ags.planning.domain;

/**
 * Enum representing the possible statuses of a project.
 */
public enum ProjectStatus {
    DRAFT,          // Initial draft status
    PLANNED,        // Project is planned but not yet started
    IN_PROGRESS,    // Project is currently active
    ON_HOLD,        // Project is temporarily paused
    COMPLETED,      // Project has been completed
    CANCELLED       // Project has been cancelled
} 