package com.stephenowinoh.Loan_calculator.Security;

import com.stephenowinoh.Loan_calculator.Jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebMvcConfigurer {

        @Autowired
        private CustomerDetailService customerDetailService;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        // PasswordEncoder Bean
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(12);
        }

        // AuthenticationProvider Bean
        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setPasswordEncoder(passwordEncoder());
                provider.setUserDetailsService(customerDetailService);
                return provider;
        }

        // AuthenticationManager Bean
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        // SecurityFilterChain Bean
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                        .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/customers/register/**", "/api/customers/authenticate") // Exclude register and login
                                .permitAll()  // Allow public access to register and login
                                .requestMatchers("/api/users/profile") // Profile endpoint: only accessible by authenticated users
                                .authenticated()  // Require authentication for /profile
                                .anyRequest()
                                .authenticated())  // Secure all other endpoints
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions (JWT-based)
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before UsernamePasswordAuthenticationFilter
                        .build();
        }


        // CORS Configuration for allowed origins
        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://loan-calculator-react.onrender.com") // Frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true); // Allow credentials such as cookies
        }
}

