package com.stephenowinoh.Loan_calculator.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

        @Value("${jwt.secret}")
        private String secret;

        private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

        // Generate JWT and return JwtPayloadDTO
        public JwtPayloadDTO generateToken(JwtPayloadDTO payload) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("iss", "https://loan-calculator.com");
                claims.put("firstName", payload.getFirstName());
                claims.put("lastName", payload.getLastName());

                // Generate the JWT token
                String token = Jwts.builder()
                        .setClaims(claims)
                        .setSubject(payload.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + VALIDITY))
                        .signWith(getSigningKey())
                        .compact();

                // Set the token in the JwtPayloadDTO
                payload.setToken(token);

                return payload;  // Return the payload including token
        }

        // Extract the username from the JWT
        public String extractUsername(String jwt) {
                return getClaims(jwt).getSubject();
        }

        // Validate the token
        public boolean isTokenValid(String jwt) {
                Claims claims = getClaims(jwt);
                return !claims.getExpiration().before(new Date()) && "https://loan-calculator.com".equals(claims.getIssuer());
        }

        // Helper method to extract claims from the JWT
        private Claims getClaims(String jwt) {
                return Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
        }

        // Generate the signing key using the secret
        private SecretKey getSigningKey() {
                return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
}
