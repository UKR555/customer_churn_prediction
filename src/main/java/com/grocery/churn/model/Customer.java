package com.grocery.churn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a grocery store customer with attributes 
 * relevant for churn prediction.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private int customerId;
    private int age;
    private String gender;
    private double income;
    private String shoppingFrequency;
    private double avgBasketValue;
    private boolean loyaltyCardMember;
    private String onlineShoppingUsage;
    private int daysLastPurchase;
    private double totalPurchaseAmount;
    private int totalVisits;
    private int complaintsMade;
    private int specialOffersRedeemed;
    private double feedbackScore;
    private String preferredPaymentMethod;
    private String preferredStoreLocation;
    private boolean churn; // target variable
    
    // For prediction input (doesn't include the ID and churn target)
    public Customer(int age, String gender, double income, String shoppingFrequency, 
                   double avgBasketValue, boolean loyaltyCardMember, String onlineShoppingUsage,
                   int daysLastPurchase, double totalPurchaseAmount, int totalVisits,
                   int complaintsMade, int specialOffersRedeemed, double feedbackScore,
                   String preferredPaymentMethod, String preferredStoreLocation) {
        this.age = age;
        this.gender = gender;
        this.income = income;
        this.shoppingFrequency = shoppingFrequency;
        this.avgBasketValue = avgBasketValue;
        this.loyaltyCardMember = loyaltyCardMember;
        this.onlineShoppingUsage = onlineShoppingUsage;
        this.daysLastPurchase = daysLastPurchase;
        this.totalPurchaseAmount = totalPurchaseAmount;
        this.totalVisits = totalVisits;
        this.complaintsMade = complaintsMade;
        this.specialOffersRedeemed = specialOffersRedeemed;
        this.feedbackScore = feedbackScore;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.preferredStoreLocation = preferredStoreLocation;
    }
} 