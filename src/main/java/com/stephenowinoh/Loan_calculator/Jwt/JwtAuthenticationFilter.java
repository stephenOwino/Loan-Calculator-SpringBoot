package com.stephenowinoh.Loan_calculator.Jwt;

import com.stephenowinoh.Loan_calculator.Security.CustomerDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JWTService jwtService;
        private final CustomerDetailService myUserDetailsService;

        @Autowired
        public JwtAuthenticationFilter(JWTService jwtService, CustomerDetailService myUserDetailsService) {
                this.jwtService = jwtService;
                this.myUserDetailsService = myUserDetailsService;
        }

        @Override
        protected void doFilterInternal(
                @NonNull HttpServletRequest request,
                @NonNull HttpServletResponse response,
                @NonNull FilterChain filterChain) throws ServletException, IOException {

                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        filterChain.doFilter(request, response);
                        return;
                }

                String token = authHeader.substring(7);
                String username = jwtService.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

                        if (jwtService.isValid(token, userDetails)) {
                                List<String> roles = jwtService.extractRoles(token); // Extract roles

                                // Convert roles to authorities with ROLE_ prefix
                                Collection<GrantedAuthority> authorities = roles.stream()
                                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Add ROLE_ prefix if not included
                                        .collect(Collectors.toList());

                                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                        userDetails, null, authorities
                                );
                                authenticationToken.setDetails(
                                        new WebAuthenticationDetailsSource().buildDetails(request)
                                );
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                                // Log the assigned authorities
                                logger.info("Assigned Authorities: " + authorities);
                        }
                }
                filterChain.doFilter(request, response);
        }
}
