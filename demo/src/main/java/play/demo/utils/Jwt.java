package play.demo.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class Jwt {

    // private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.secret}")
    private String key;

    public String generateToken(String userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId.toString())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 86400000))
                .signWith(signingKey())
                .compact();
    }

    private Key signingKey() {
        // HS256 key
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public static String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    public String getUserIdFromToken(String token) {
        Object userId = extractClaims(token).get("userId");
        if (userId == null)
            return null;
        return (String) userId;
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}