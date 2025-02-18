package com.stephenowinoh.Loan_calculator.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class JWTService {

        @Value("${jwt.secretKey}")
        private String secretKey;

        @Value("${jwt.expirationTime}")
        private long expirationTime;

        private static final Logger logger = Logger.getLogger(JWTService.class.getName());

        // Generate token including roles
        public String generateToken(JwtPayloadDTO jwtPayloadDTO) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("customerId", jwtPayloadDTO.getId());
                claims.put("roles", jwtPayloadDTO.getRoles());  // Store roles as a List

                return Jwts.builder()
                        .setClaims(claims)
                        .setSubject(jwtPayloadDTO.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(getKey(), SignatureAlgorithm.HS256)
                        .compact();
        }

        // Extract username from token
        public String extractUsername(String token) {
                try {
                        return extractClaim(token, Claims::getSubject);
                } catch (Exception e) {
                        logger.warning("Error extracting username from token: " + e.getMessage());
                        return null;
                }
        }

        // Extract roles from token
        public List<String> extractRoles(String token) {
                try {
                        // Extract roles as a List from claims
                        return extractClaim(token, claims -> claims.get("roles", List.class));
                } catch (Exception e) {
                        logger.warning("Error extracting roles from token: " + e.getMessage());
                        return Collections.emptyList();
                }
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
        public boolean isValid(String token, UserDetails userDetails) {
                try {
                        final String username = extractUsername(token);
                        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
                } catch (Exception e) {
                        logger.warning("Error validating token: " + e.getMessage());
                        return false;
                }
        }

        // Check if the token has expired
        private boolean isTokenExpired(String token) {
                try {
                        return extractExpiration(token).before(new Date());
                } catch (Exception e) {
                        logger.warning("Error checking token expiration: " + e.getMessage());
                        return true; // Consider expired if an error occurs
                }
        }

        // Extract expiration date from token
        private Date extractExpiration(String token) {
                return extractClaim(token, Claims::getExpiration);
        }

        // Helper method to get the signing key
        private SecretKey getKey() {
                byte[] keyBytes = Decoders.BASE64.decode(secretKey);
                return Keys.hmacShaKeyFor(keyBytes);
        }

        // --- Additional Methods Below ---

        // Method to validate token based on only username
        public boolean validateTokenByUsername(String token, String username) {
                try {
                        final String extractedUsername = extractUsername(token);
                        return (extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token));
                } catch (Exception e) {
                        logger.warning("Error validating token by username: " + e.getMessage());
                        return false;
                }
        }

        // Generate token for userDetails only (for simple user cases)
        public String generateTokenForUserDetails(UserDetails userDetails) {
                // Extract roles from user details
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                Map<String, Object> claims = new HashMap<>();
                claims.put("roles", roles);  // Store roles directly as authorities

                return Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(getKey(), SignatureAlgorithm.HS256)
                        .compact();
        }

        // Method to extract specific claim (like first name, email, etc.) from token
        public String extractClaimByName(String token, String claimName) {
                try {
                        return extractClaim(token, claims -> claims.get(claimName, String.class));
                } catch (Exception e) {
                        logger.warning("Error extracting claim (" + claimName + ") from token: " + e.getMessage());
                        return null;
                }
        }

        // Method to generate a refreshed token with new expiration
        public String refreshToken(String token) {
                try {
                        String username = extractUsername(token);
                        List<String> roles = extractRoles(token);
                        Date now = new Date(System.currentTimeMillis());

                        Map<String, Object> claims = new HashMap<>();
                        claims.put("roles", roles);

                        return Jwts.builder()
                                .setClaims(claims)
                                .setSubject(username)
                                .setIssuedAt(now)
                                .setExpiration(new Date(now.getTime() + expirationTime))
                                .signWith(getKey(), SignatureAlgorithm.HS256)
                                .compact();
                } catch (Exception e) {
                        logger.warning("Error refreshing token: " + e.getMessage());
                        return null;
                }
        }
}

