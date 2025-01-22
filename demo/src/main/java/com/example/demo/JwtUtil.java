// package  com.example.demo;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;


// import org.springframework.stereotype.Component;

// import java.util.Date;

// @Component
// public class JwtUtil {

//     private final String SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).toString();

//     // Generate token
//     public String generateToken(String username) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
//                 .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                 .compact();
//     }

//     // Extract username from token
//     public String extractUsername(String token) {
//         return extractClaims(token).getSubject();
//     }

//     // Check if the token is expired
//     public boolean isTokenExpired(String token) {
//         return extractClaims(token).getExpiration().before(new Date());
//     }

//     // Validate token
//     public boolean validateToken(String token, String username) {
//         return extractUsername(token).equals(username) && !isTokenExpired(token);
//     }

//     // Extract all claims
//     private Claims extractClaims(String token) {
//         return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//     }
// }


package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    // Generate a secure secret key for HS256
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Generate token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(SECRET_KEY) // Use the secure key directly
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Validate token
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // Extract all claims
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Use the secure key for validation
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
