package com.arogyam.health.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret:mySecretKey123456789012345678901234567890}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}") // 24 hours in milliseconds
    private Long jwtExpirationMs;

    private SecretKey getSigningKey(){
        // Use StandardCharsets.UTF_8 instead of getBytes() for consistency
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userPrincipal.getId());
        claims.put("role", userPrincipal.getRole().name());
        claims.put("fullName", userPrincipal.getFullName());

        return generateToken(claims, userPrincipal.getUsername());
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims) // Use claims() instead of setClaims() in newer version
                .subject(subject) // Use subject() instead of setSubject()
                .issuedAt(new Date()) // Use issuedAt() instead of setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Use expiration() instead of setExpiration()
                .signWith(getSigningKey()) // signWith() method signature is same
                .compact();
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Object userId = claims.get("userId");
        if (userId instanceof Number) {
            return ((Number) userId).longValue();
        }
        return Long.valueOf(userId.toString());
    }

    public String getRoleFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("role").toString();
    }

    public String getFullNameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Object fullName = claims.get("fullName");
        return fullName != null ? fullName.toString() : null;
    }

    public <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        try{
            return Jwts.parser() // In v0.12.3, parser() method is back and preferred
                    .verifyWith(getSigningKey()) // Use verifyWith() instead of setSigningKey()
                    .build()
                    .parseSignedClaims(token) // Use parseSignedClaims() instead of parseClaimsJws()
                    .getPayload(); // Use getPayload() instead of getBody()
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("JWT token is invalid", e);
        }
    }

    public boolean isTokenExpired(String token){
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails){
        try{
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Method to refresh token (generate new token with same claims but extended expiry)
    public String refreshToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            Map<String, Object> claimsMap = new HashMap<>(claims);
            // Remove standard claims to avoid duplication
            claimsMap.remove("sub");
            claimsMap.remove("iat");
            claimsMap.remove("exp");
            return generateToken(claimsMap, claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException("Cannot refresh invalid token", e);
        }
    }
}