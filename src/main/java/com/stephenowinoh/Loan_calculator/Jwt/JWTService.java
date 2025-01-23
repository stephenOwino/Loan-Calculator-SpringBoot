package com.stephenowinoh.Loan_calculator.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

        public String secretKey = "";

        public JWTService() {
                try {
                        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
                        SecretKey sk = keyGen.generateKey();
                        secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
                } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                }
        }
        public String generateToken(JwtPayloadDTO customer) {
                Map<String, Object> claims = new HashMap<>();
                // You can add custom claims to your token here
                claims.put("firstName", customer.getFirstName());
                claims.put("lastName", customer.getLastName());

                return Jwts.builder()
                        .setClaims(claims)
                        .setSubject(customer.getUsername())  // Using username from JwtPayloadDTO
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))  // 30 minutes expiration
                        .signWith(getKey())  // Signing with the secret key
                        .compact();
        }

        private SecretKey getKey() {
                byte[] keyBytes = Decoders.BASE64.decode(secretKey);
                return Keys.hmacShaKeyFor(keyBytes);
        }

        public String extractUserName(String token) {
                return extractClaim(token, Claims::getSubject);
        }

        private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
                final Claims claims = extractAllClaims(token);
                return claimResolver.apply(claims);
        }

        private Claims extractAllClaims(String token){
                return Jwts.parser()
                        .verifyWith(getKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
        }
        public boolean validateToken(String token, UserDetails userDetails) {
                final String username = extractUserName(token);
                return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        private boolean isTokenExpired(String token) {
                return extractExpiration(token).before(new Date());
        }

        private Date extractExpiration(String token) {
                return extractClaim(token, Claims::getExpiration);
        }



}
