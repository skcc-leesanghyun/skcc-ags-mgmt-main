package com.skcc.ags.talent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for proposal feedback statistics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposalFeedbackStatisticsDTO {

    private String department;
    
    private Double averageScore;
    
    private long totalFeedback;
    
    private long pendingFeedback;
    
    private long completedFeedback;
    
    private Map<String, Double> scoresByCategory;
    
    private Double technicalScoreAverage;
    
    private Double experienceScoreAverage;
    
    private Double costScoreAverage;
    
    private long feedbackRequiringAttention;
    
    private Double finalReviewPercentage;
    
    private Map<String, Long> feedbackCountByMonth;

    /**
     * Calculate the completion rate of feedback.
     *
     * @return the percentage of completed feedback
     */
    public Double getCompletionRate() {
        return totalFeedback > 0 ? 
               (completedFeedback * 100.0) / totalFeedback : 0.0;
    }

    /**
     * Calculate the attention rate (percentage of feedback requiring attention).
     *
     * @return the percentage of feedback requiring attention
     */
    public Double getAttentionRate() {
        return totalFeedback > 0 ? 
               (feedbackRequiringAttention * 100.0) / totalFeedback : 0.0;
    }

    /**
     * Get the overall performance score.
     * This is calculated as a weighted average of all score categories.
     *
     * @return the overall performance score
     */
    public Double getOverallPerformanceScore() {
        if (technicalScoreAverage == null || experienceScoreAverage == null || costScoreAverage == null) {
            return null;
        }

        double technicalWeight = 0.4;
        double experienceWeight = 0.3;
        double costWeight = 0.3;

        return (technicalScoreAverage * technicalWeight) +
               (experienceScoreAverage * experienceWeight) +
               (costScoreAverage * costWeight);
    }
} 