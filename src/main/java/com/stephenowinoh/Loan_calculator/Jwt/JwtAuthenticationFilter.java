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
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        logger.warning("Missing or invalid Authorization header");
                        filterChain.doFilter(request, response);
                        return;
                }

                String token = authHeader.substring(7);
                String username = jwtService.extractUserName(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

                        if (jwtService.validateToken(token, userDetails)) {
                                // Extract roles from JWT
                                List<String> roles = jwtService.extractRoles(token);
                                var authorities = roles.stream()
                                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                        .collect(Collectors.toList());

                                UsernamePasswordAuthenticationToken authToken =
                                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                                logger.info("Authenticated user: " + username + " with roles: " + roles);
                        } else {
                                logger.warning("Invalid JWT token for user: " + username);
                        }
                }

                filterChain.doFilter(request, response);
        }
}