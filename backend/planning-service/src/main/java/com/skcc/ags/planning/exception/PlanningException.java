package com.skcc.ags.planning.exception;

import org.springframework.http.HttpStatus;

public class PlanningException extends RuntimeException {
    private final HttpStatus status;
    private final String type;

    public PlanningException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, "PLANNING_ERROR", message);
    }

    public PlanningException(HttpStatus status, String message) {
        this(status, "PLANNING_ERROR", message);
    }

    public PlanningException(HttpStatus status, String type, String message) {
        super(message);
        this.status = status;
        this.type = type;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}

class MonthlyPlanNotFoundException extends PlanningException {
    public MonthlyPlanNotFoundException(Long id) {
        super("Monthly plan not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}

class InvalidMonthlyPlanException extends PlanningException {
    public InvalidMonthlyPlanException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}

class DuplicateMonthlyPlanException extends PlanningException {
    public DuplicateMonthlyPlanException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
} 