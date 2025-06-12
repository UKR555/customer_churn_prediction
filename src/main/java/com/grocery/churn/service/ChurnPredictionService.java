package com.grocery.churn.service;

import com.grocery.churn.model.Customer;
import com.grocery.churn.model.PredictionResult;
import com.grocery.churn.util.CSVDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToNominal;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service responsible for churn prediction using Weka ML models.
 */
@Service
@Slf4j
public class ChurnPredictionService {

    @Autowired
    private CSVDataLoader csvDataLoader;

    private Classifier bestModel;
    private String bestModelName;
    private ArrayList<Attribute> attributes;
    private Instances datasetStructure;
    
    /**
     * Initialize the service and train the model on application startup
     */
    @PostConstruct
    public void init() {
        try {
            log.info("Initializing ChurnPredictionService...");
            List<Customer> customers = csvDataLoader.loadCustomerData();
            trainModel(customers);
            log.info("ChurnPredictionService initialized successfully with best model: {}", bestModelName);
        } catch (Exception e) {
            log.error("Error initializing ChurnPredictionService", e);
        }
    }
    
    /**
     * Train multiple models and select the best one based on accuracy
     */
    private void trainModel(List<Customer> customers) throws Exception {
        // Create attributes list
        attributes = new ArrayList<>();
        
        // Add all attributes
        attributes.add(new Attribute("age"));
        
        // Gender is a nominal attribute
        ArrayList<String> genderValues = new ArrayList<>();
        genderValues.add("Male");
        genderValues.add("Female");
        attributes.add(new Attribute("gender", genderValues));
        
        attributes.add(new Attribute("income"));
        
        // Shopping frequency is a nominal attribute
        ArrayList<String> frequencyValues = new ArrayList<>();
        frequencyValues.add("Weekly");
        frequencyValues.add("Biweekly");
        frequencyValues.add("Monthly");
        attributes.add(new Attribute("shoppingFrequency", frequencyValues));
        
        attributes.add(new Attribute("avgBasketValue"));
        
        // Loyalty card is a nominal attribute
        ArrayList<String> loyaltyValues = new ArrayList<>();
        loyaltyValues.add("true");
        loyaltyValues.add("false");
        attributes.add(new Attribute("loyaltyCardMember", loyaltyValues));
        
        // Online shopping usage is a nominal attribute
        ArrayList<String> onlineUsageValues = new ArrayList<>();
        onlineUsageValues.add("Low");
        onlineUsageValues.add("Medium");
        onlineUsageValues.add("High");
        attributes.add(new Attribute("onlineShoppingUsage", onlineUsageValues));
        
        attributes.add(new Attribute("daysLastPurchase"));
        attributes.add(new Attribute("totalPurchaseAmount"));
        attributes.add(new Attribute("totalVisits"));
        attributes.add(new Attribute("complaintsMade"));
        attributes.add(new Attribute("specialOffersRedeemed"));
        attributes.add(new Attribute("feedbackScore"));
        
        // Payment method is a nominal attribute
        ArrayList<String> paymentValues = new ArrayList<>();
        paymentValues.add("Credit Card");
        paymentValues.add("Debit Card");
        paymentValues.add("Cash");
        attributes.add(new Attribute("preferredPaymentMethod", paymentValues));
        
        // Store location is a nominal attribute
        ArrayList<String> locationValues = new ArrayList<>();
        locationValues.add("Downtown");
        locationValues.add("Suburb");
        locationValues.add("Mall");
        attributes.add(new Attribute("preferredStoreLocation", locationValues));
        
        // Class attribute (churn)
        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("Yes");
        classValues.add("No");
        attributes.add(new Attribute("churn", classValues));
        
        // Create dataset with these attributes
        Instances dataset = new Instances("GroceryCustomers", attributes, customers.size());
        dataset.setClassIndex(attributes.size() - 1);
        
        // Save dataset structure for later predictions
        datasetStructure = new Instances(dataset, 0);
        
        // Add customer data to instances
        for (Customer customer : customers) {
            Instance instance = createInstance(customer, dataset);
            dataset.add(instance);
        }
        
        log.info("Created dataset with {} instances and {} attributes", dataset.numInstances(), dataset.numAttributes());
        
        // Split into training and testing sets (80-20 split)
        dataset.randomize(new Random(42));
        int trainSize = (int) Math.round(dataset.numInstances() * 0.8);
        int testSize = dataset.numInstances() - trainSize;
        Instances trainingSet = new Instances(dataset, 0, trainSize);
        Instances testingSet = new Instances(dataset, trainSize, testSize);
        
        log.info("Split data into {} training instances and {} testing instances", trainingSet.numInstances(), testingSet.numInstances());
        
        // Train multiple models and evaluate
        List<Classifier> models = new ArrayList<>();
        List<String> modelNames = new ArrayList<>();
        
        // Add different models
        models.add(new J48());
        modelNames.add("Decision Tree (J48)");
        
        models.add(new RandomForest());
        modelNames.add("Random Forest");
        
        models.add(new NaiveBayes());
        modelNames.add("Naive Bayes");
        
        models.add(new Logistic());
        modelNames.add("Logistic Regression");
        
        models.add(new SMO());
        modelNames.add("Support Vector Machine (SMO)");
        
        // Evaluate and find best model
        double bestAccuracy = 0;
        for (int i = 0; i < models.size(); i++) {
            Classifier model = models.get(i);
            String modelName = modelNames.get(i);
            
            model.buildClassifier(trainingSet);
            
            Evaluation evaluation = new Evaluation(trainingSet);
            evaluation.evaluateModel(model, testingSet);
            
            double accuracy = evaluation.pctCorrect();
            log.info("Model: {}, Accuracy: {}%", modelName, accuracy);
            
            if (accuracy > bestAccuracy) {
                bestAccuracy = accuracy;
                bestModel = model;
                bestModelName = modelName;
            }
        }
        
        log.info("Best model: {} with accuracy: {}%", bestModelName, bestAccuracy);
    }
    
    /**
     * Creates a Weka Instance from a Customer object
     */
    private Instance createInstance(Customer customer, Instances dataset) {
        Instance instance = new DenseInstance(attributes.size());
        instance.setDataset(dataset);
        
        instance.setValue(0, customer.getAge());
        instance.setValue(1, customer.getGender());
        instance.setValue(2, customer.getIncome());
        instance.setValue(3, customer.getShoppingFrequency());
        instance.setValue(4, customer.getAvgBasketValue());
        instance.setValue(5, String.valueOf(customer.isLoyaltyCardMember()));
        instance.setValue(6, customer.getOnlineShoppingUsage());
        instance.setValue(7, customer.getDaysLastPurchase());
        instance.setValue(8, customer.getTotalPurchaseAmount());
        instance.setValue(9, customer.getTotalVisits());
        instance.setValue(10, customer.getComplaintsMade());
        instance.setValue(11, customer.getSpecialOffersRedeemed());
        instance.setValue(12, customer.getFeedbackScore());
        instance.setValue(13, customer.getPreferredPaymentMethod());
        instance.setValue(14, customer.getPreferredStoreLocation());
        
        // Set class value if available (not for prediction instances)
        if (dataset.classIndex() >= 0) {
            instance.setValue(15, customer.isChurn() ? "Yes" : "No");
        }
        
        return instance;
    }
    
    /**
     * Predict if a customer will churn
     */
    public PredictionResult predictChurn(Customer customer) {
        try {
            // Create test instance
            Instance testInstance = createInstance(customer, datasetStructure);
            
            // Make prediction
            double[] distribution = bestModel.distributionForInstance(testInstance);
            double churnProbability = distribution[0]; // "Yes" probability
            String prediction = churnProbability > 0.5 ? "Yes" : "No";
            
            return new PredictionResult(prediction, churnProbability, bestModelName);
        } catch (Exception e) {
            log.error("Error predicting churn", e);
            return new PredictionResult("Error", 0.0, "None");
        }
    }
    
    /**
     * Get the name of the currently used model
     */
    public String getModelName() {
        return bestModelName;
    }
} 