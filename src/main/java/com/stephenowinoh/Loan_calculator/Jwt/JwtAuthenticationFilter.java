package com.stephenowinoh.Loan_calculator.Jwt;

import com.stephenowinoh.Loan_calculator.Security.CustomerDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        @Autowired
        private JWTService jwtService;

        @Autowired
        private CustomerDetailService myUserDetailsService;

        private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
                String authHeader = request.getHeader("Authorization");

                // Check for missing or invalid Authorization header
                if (authHeader == null || ! authHeader.startsWith("Bearer ")) {
                        logger.warning("Missing or invalid Authorization header");
                        filterChain.doFilter(request, response);
                        return;
                }

                String token = authHeader.substring(7);
                String username = jwtService.extractUserName(token);

                // Check if username is valid and if the user is not already authenticated
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

                        if (jwtService.validateToken(token, userDetails)) {
                                // Extract roles from JWT and set the authentication
                                List<String> roles = jwtService.extractRoles(token);
                                var authorities = roles.stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());

                                UsernamePasswordAuthenticationToken authToken =
                                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);

                                logger.info("Authenticated user: " + username + " with roles: " + roles);
                        } else {
                                logger.warning("Invalid JWT token for user: " + username);
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("Invalid or expired JWT token");
                                return; // Stop further processing as token is invalid
                        }
                } else {
                        logger.warning("Username is null or already authenticated");
                }

                filterChain.doFilter(request, response);
        }
}