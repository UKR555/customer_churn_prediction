# Grocery Customer Churn Prediction

A Java web application that predicts grocery store customer churn using Weka machine learning library.

## Overview

This application uses machine learning to predict whether a grocery store customer is likely to churn (stop shopping) based on their demographics and shopping behavior data. It helps grocery retailers identify at-risk customers and take proactive measures to retain them.

## Features

- Machine learning-based churn prediction
- Interactive web interface for prediction
- Multiple ML algorithms with automatic selection of the best performer
- Detailed prediction results with probability and risk categorization
- Personalized retention strategy recommendations

## Technology Stack

### Backend
- Java 11
- Spring Boot 2.7.3
- Weka 3.8.6 (Machine Learning)
- Maven (Build Tool)

### Frontend
- Thymeleaf (Server-side Template Engine)
- Bootstrap 5.2.3
- HTML/CSS/JavaScript

## Machine Learning Models

The application evaluates several machine learning algorithms and automatically selects the highest-performing model for making predictions:
- Decision Tree (J48)
- Random Forest
- Naive Bayes
- Logistic Regression
- Support Vector Machine (SMO)

## Dataset

The model is trained on a dataset that includes the following customer features:
- Demographics (age, gender, income)
- Shopping frequency
- Average basket value
- Loyalty program membership
- Online shopping usage
- Days since last purchase
- Total purchase amount
- Store visit frequency
- Complaints made
- Special offers redeemed
- Customer feedback scores
- Preferred payment method and store location

## Getting Started

### Prerequisites
- JDK 11 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```
git clone https://github.com/yourusername/grocery-churn-prediction.git
cd grocery-churn-prediction
```

2. Build the application:
```
mvn clean package
```

3. Run the application:
```
java -jar target/grocery-churn-prediction-1.0.0.jar
```

4. Open a web browser and navigate to:
```
http://localhost:8080
```

## Usage

1. Navigate to the "Predict" page
2. Enter customer details in the form
3. Click "Predict Churn" to see the prediction results
4. Review the prediction outcome and recommended retention actions for at-risk customers

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Weka machine learning library - https://www.cs.waikato.ac.nz/ml/weka/
- Spring Boot - https://spring.io/projects/spring-boot
- Bootstrap - https://getbootstrap.com/ 