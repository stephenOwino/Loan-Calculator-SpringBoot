package com.stephenowinoh.Loan_calculator.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS to all endpoints
                        .allowedOrigins(
                                "https://loan-calculator-react.onrender.com", // Deployed React app
                                "http://localhost:5173" // Local React app for testing
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                        .allowedHeaders("Authorization", "Content-Type") // Allow required headers
                        .allowCredentials(true); // Support credentials (cookies/authorization headers)
        }
}
