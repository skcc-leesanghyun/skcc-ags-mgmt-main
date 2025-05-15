package com.skcc.ags.planning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class for AGS Planning and Performance Management Service.
 * This service handles project planning, manpower allocation, and performance tracking.
 */
@SpringBootApplication
@EnableTransactionManagement
public class PlanningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanningServiceApplication.class, args);
    }
} 