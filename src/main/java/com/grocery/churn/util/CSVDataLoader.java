package com.grocery.churn.util;

import com.grocery.churn.model.Customer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to load customer data from CSV file.
 */
@Component
public class CSVDataLoader {

    /**
     * Load customer data from the CSV file.
     *
     * @return List of Customer objects
     * @throws IOException if there's an error reading the file
     */
    public List<Customer> loadCustomerData() throws IOException {
        List<Customer> customers = new ArrayList<>();
        
        try {
            // Try to load the file from the filesystem
            File file = new File("data/grocery_customers.csv");
            
            if (!file.exists()) {
                // If not found, try to load from classpath
                file = ResourceUtils.getFile("classpath:data/grocery_customers.csv");
            }
            
            Reader reader = new FileReader(file);
            
            // Parse the CSV file
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            
            // Process each record
            for (CSVRecord record : csvParser) {
                Customer customer = new Customer();
                customer.setCustomerId(Integer.parseInt(record.get("CustomerID")));
                customer.setAge(Integer.parseInt(record.get("Age")));
                customer.setGender(record.get("Gender"));
                customer.setIncome(Double.parseDouble(record.get("Income")));
                customer.setShoppingFrequency(record.get("ShoppingFrequency"));
                customer.setAvgBasketValue(Double.parseDouble(record.get("AvgBasketValue")));
                customer.setLoyaltyCardMember("Yes".equalsIgnoreCase(record.get("LoyaltyCardMember")));
                customer.setOnlineShoppingUsage(record.get("OnlineShoppingUsage"));
                customer.setDaysLastPurchase(Integer.parseInt(record.get("DaysLastPurchase")));
                customer.setTotalPurchaseAmount(Double.parseDouble(record.get("TotalPurchaseAmount")));
                customer.setTotalVisits(Integer.parseInt(record.get("TotalVisits")));
                customer.setComplaintsMade(Integer.parseInt(record.get("ComplaintsMade")));
                customer.setSpecialOffersRedeemed(Integer.parseInt(record.get("SpecialOffersRedeemed")));
                customer.setFeedbackScore(Double.parseDouble(record.get("FeedbackScore")));
                customer.setPreferredPaymentMethod(record.get("PreferredPaymentMethod"));
                customer.setPreferredStoreLocation(record.get("PreferredStoreLocation"));
                customer.setChurn("Yes".equalsIgnoreCase(record.get("Churn")));
                
                customers.add(customer);
            }
            
            csvParser.close();
            reader.close();
        } catch (Exception e) {
            throw new IOException("Error loading customer data: " + e.getMessage(), e);
        }
        
        return customers;
    }
} 