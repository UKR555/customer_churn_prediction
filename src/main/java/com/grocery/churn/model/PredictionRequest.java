package com.grocery.churn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to capture the prediction request form data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionRequest {
    private int age;
    private String gender;
    private double income;
    private String shoppingFrequency;
    private double avgBasketValue;
    private String loyaltyCardMember; // "Yes" or "No"
    private String onlineShoppingUsage; // "Low", "Medium", "High"
    private int daysLastPurchase;
    private double totalPurchaseAmount;
    private int totalVisits;
    private int complaintsMade;
    private int specialOffersRedeemed;
    private double feedbackScore;
    private String preferredPaymentMethod;
    private String preferredStoreLocation;
    
    public Customer toCustomer() {
        return new Customer(
            age, 
            gender, 
            income, 
            shoppingFrequency, 
            avgBasketValue,
            "Yes".equalsIgnoreCase(loyaltyCardMember),
            onlineShoppingUsage,
            daysLastPurchase,
            totalPurchaseAmount,
            totalVisits,
            complaintsMade,
            specialOffersRedeemed,
            feedbackScore,
            preferredPaymentMethod,
            preferredStoreLocation
        );
    }
} 