package com.stephenowinoh.Loan_calculator.Jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

        // Secret key for signing the JWT (this should be securely stored, not hardcoded)
        private static final String SECRET = "638CBE3A90E0303BF3808F40F95A7F02A24B4B5D029C954CF553F79E9EF1DC0384BE681C249F1223F6B55AA21DC070914834CA22C8DD98E14A872CA010091ACC";
        private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);  // Set token validity to 30 minutes

        // Method to generate JWT token
        public String generateToken(UserDetails userDetails, String firstName, String lastName) {
                // Include custom claims (e.g., customer-related data)
                Map<String, String> claims = new HashMap<>();
                claims.put("iss", "https://loan-calculator.com");  // Issuer for the JWT
                claims.put("firstName", firstName);  // Add customer first name as claim
                claims.put("lastName", lastName);    // Add customer last name as claim

                // Build the JWT with the claims and subject
                return Jwts.builder()
                        .claims(claims)  // Set claims
                        .subject(userDetails.getUsername())  // Set the username as the subject
                        .issuedAt(Date.from(Instant.now()))  // Set the issued time
                        .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))  // Set the expiration time
                        .signWith(generateKey())  // Sign the JWT with the secret key
                        .compact();  // Return the compact JWT string
        }

        // Generate secret key for JWT signing
        private SecretKey generateKey() {
                byte[] decodedKey = Base64.getDecoder().decode(SECRET);
                return Keys.hmacShaKeyFor(decodedKey);  // Return the generated secret key
        }

        // Extract the username from the JWT
        public String extractUsername(String jwt) {
                Claims claims = getClaims(jwt);  // Get claims from the JWT
                return claims.getSubject();  // Return the username (subject) from the claims
        }

        // Extract the first name from the JWT
        public String extractFirstName(String jwt) {
                Claims claims = getClaims(jwt);  // Get claims from the JWT
                return claims.get("firstName", String.class);  // Extract first name from the claims
        }

        // Extract the last name from the JWT
        public String extractLastName(String jwt) {
                Claims claims = getClaims(jwt);  // Get claims from the JWT
                return claims.get("lastName", String.class);  // Extract last name from the claims
        }

        // Get claims from the JWT
        private Claims getClaims(String jwt) {
                return Jwts.parser()
                        .setSigningKey(generateKey())  // Set the secret key for validation
                        .parseClaimsJws(jwt)  // Parse the JWT
                        .getBody();  // Return the claims body
        }

        // Validate if the JWT is still valid
        public boolean isTokenValid(String jwt) {
                Claims claims = getClaims(jwt);  // Get claims from the JWT
                return claims.getExpiration().after(Date.from(Instant.now()));  // Check if the expiration time is after the current time
        }
}
