package com.stephenowinoh.Loan_calculator.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://loan-calculator-react.onrender.com") // Allow only your React app
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow the HTTP methods your app needs
                        .allowedHeaders("Authorization", "Content-Type") // Allow specific headers
                        .allowCredentials(true); // Allow sending credentials (cookies, headers like Authorization)
        }
}
