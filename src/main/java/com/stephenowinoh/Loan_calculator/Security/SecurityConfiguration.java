package com.stephenowinoh.Loan_calculator.Security;

import com.stephenowinoh.Loan_calculator.Jwt.JwtAuthenticationFilter;
import com.stephenowinoh.Loan_calculator.Role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Autowired
        private CustomerDetailService customerDetailService;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(12);
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setPasswordEncoder(passwordEncoder());
                provider.setUserDetailsService(customerDetailService);
                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                        .cors(Customizer.withDefaults()) // Use the default CORS configuration
                        .authorizeHttpRequests(auth -> auth
                                // Allow unauthenticated access to registration and authentication endpoints
                                .requestMatchers("/api/customers/register/**", "/api/customers/authenticate").permitAll()
                                // Role-based access for different endpoints
                                .requestMatchers("/api/admin/**").hasAuthority(Role.ADMIN.name()) // Admin-only endpoints
                                .requestMatchers("/api/customer/**").hasAuthority(Role.CUSTOMER.name()) // Customer-only endpoints
                                .anyRequest().authenticated()) // Default authentication for other routes
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = getCorsConfiguration();
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);  // Apply the configuration to all routes

                return source;
        }

        private static CorsConfiguration getCorsConfiguration() {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOrigin("https://loan-calculator-react.onrender.com");  // Ensure this matches your React app URL
                config.addAllowedMethod("GET");
                config.addAllowedMethod("POST");
                config.addAllowedMethod("PUT");
                config.addAllowedMethod("DELETE");
                config.addAllowedHeader("*");  // Allow all headers, including 'Authorization'
                config.addAllowedHeader("Authorization");
                return config;
        }
}
