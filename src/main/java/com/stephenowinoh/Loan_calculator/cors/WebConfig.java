package com.stephenowinoh.Loan_calculator.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "https://loan-calculator-react.onrender.com", // Your deployed React app
                                "http://localhost:5173" // Your local React app for testing
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed HTTP methods
                        .allowedHeaders("Authorization", "Content-Type") // Allow specific headers
                        .allowCredentials(true); // Allow cookies and Authorization header
        }
}
