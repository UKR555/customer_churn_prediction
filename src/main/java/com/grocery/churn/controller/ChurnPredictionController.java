package com.grocery.churn.controller;

import com.grocery.churn.model.Customer;
import com.grocery.churn.model.PredictionRequest;
import com.grocery.churn.model.PredictionResult;
import com.grocery.churn.service.ChurnPredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling the churn prediction web interface.
 */
@Controller
@Slf4j
public class ChurnPredictionController {

    @Autowired
    private ChurnPredictionService churnPredictionService;

    /**
     * Display the home page
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("modelName", churnPredictionService.getModelName());
        return "index";
    }
    
    /**
     * Display the prediction form
     */
    @GetMapping("/predict")
    public String showPredictionForm(Model model) {
        model.addAttribute("predictionRequest", new PredictionRequest());
        return "prediction-form";
    }
    
    /**
     * Process the prediction form submission
     */
    @PostMapping("/predict")
    public String processPrediction(@ModelAttribute PredictionRequest predictionRequest, Model model) {
        log.info("Processing prediction request: {}", predictionRequest);
        
        // Convert form data to Customer object
        Customer customer = predictionRequest.toCustomer();
        
        // Get prediction from service
        PredictionResult result = churnPredictionService.predictChurn(customer);
        log.info("Prediction result: {}", result);
        
        // Add result to model for display
        model.addAttribute("result", result);
        model.addAttribute("customer", customer);
        model.addAttribute("predictionRequest", predictionRequest);
        
        return "prediction-result";
    }
    
    /**
     * Display the about page with information about the project
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
} 