package com.grocery.churn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to represent the result of a churn prediction.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResult {
    private String prediction; // "Yes" or "No"
    private double probability; // Probability of churning
    private String algorithm; // The algorithm used for prediction
    
    // Additional information for the UI
    private String churnRiskCategory; // "High", "Medium", "Low"
    
    public PredictionResult(String prediction, double probability, String algorithm) {
        this.prediction = prediction;
        this.probability = probability;
        this.algorithm = algorithm;
        
        // Calculate risk category based on probability
        if (probability >= 0.7) {
            this.churnRiskCategory = "High";
        } else if (probability >= 0.3) {
            this.churnRiskCategory = "Medium";
        } else {
            this.churnRiskCategory = "Low";
        }
    }
} 