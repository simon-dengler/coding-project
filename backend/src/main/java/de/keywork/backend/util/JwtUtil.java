package de.keywork.backend.util;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

/**
 * Creates and validates Jason Web Tokens (JWTs).
 */
@Component
public class JwtUtil {

    private final String SECRET;
    // for testability
    public JwtUtil(@Value("${app.jwt.secret}") String secret){
        this.SECRET = secret;
    }

    /**
     * Creates JWT with expiration date for username.
     * @param username
     * @return
     */
    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(key)
                .compact();
    }

    /**
     * Validates a JWT with the secret and returns the contained username (if valid).
     * @param token
     * @return
     */
    public String validateAndExtractUsername(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        try {
            Claims claims = (Claims) Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parse(token)
                    .getPayload();
            return claims.getSubject();
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * Validates a JWT regarding matching username data and expiration date.
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isValidToken(String token, UserDetails userDetails) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        try {
            Claims claims = (Claims) Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parse(token)
                    .getPayload();
            Date expiration = claims.getExpiration();
            String username = claims.getSubject();
            if (username.equals(userDetails.getUsername())
             && expiration.after(Date.from(Instant.now()))){
                return true;
            } else {
                return false;
            }
        } catch (JwtException e) {
            return false;
        }
    }
}
