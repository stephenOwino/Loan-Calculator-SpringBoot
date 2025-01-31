package com.stephenowinoh.Loan_calculator.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

        @Value("${jwt.secretKey}")
        private String secretKey;

        @Value("${jwt.expirationTime}")
        private long expirationTime;  // expiration time in milliseconds

        // Generate token including roles
        public String generateToken(JwtPayloadDTO jwtPayloadDTO) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("userId", jwtPayloadDTO.getId());
                claims.put("roles", jwtPayloadDTO.getRoles()); // Store roles as a List

                return Jwts.builder()
                        .setClaims(claims)
                        .setSubject(jwtPayloadDTO.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(getKey(), SignatureAlgorithm.HS256)  // Use a fixed key
                        .compact();
        }

        // Extract username from token
        public String extractUserName(String token) {
                return extractClaim(token, Claims::getSubject);
        }

        // Extract roles from token (Fixed: Now correctly fetching "roles" instead of "token")
        public List<String> extractRoles(String token) {
                return extractClaim(token, claims -> claims.get("roles", List.class));
        }

        // Extract specific claims
        private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
                final Claims claims = extractAllClaims(token);
                return claimResolver.apply(claims);
        }

        // Parse all claims from the token
        private Claims extractAllClaims(String token) {
                return Jwts.parserBuilder()
                        .setSigningKey(getKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        }

        // Validate the token
        public boolean validateToken(String token, UserDetails userDetails) {
                final String username = extractUserName(token);
                return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        // Check if the token has expired
        private boolean isTokenExpired(String token) {
                return extractExpiration(token).before(new Date());
        }

        // Extract expiration date from token
        private Date extractExpiration(String token) {
                return extractClaim(token, Claims::getExpiration);
        }

        // Helper method to get the signing key (Fixed: Now uses a fixed secret key)
        private SecretKey getKey() {
                byte[] keyBytes = Decoders.BASE64.decode(secretKey);
                return Keys.hmacShaKeyFor(keyBytes);
        }

}
