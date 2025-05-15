package com.ags.talent.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CandidateFilterDTO {
    private String name;
    private Set<String> skills;
    private Integer minYearsOfExperience;
    private Integer maxYearsOfExperience;
    private Double minScore;
} 