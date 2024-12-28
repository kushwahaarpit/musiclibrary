package com.assignment.musiclibrary.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    private static final String SECRET_KEY = "secret";

    // Method to generate a JWT token
    public String generateToken(String email, String userId) {
        return Jwts.builder()
                .setSubject(email) // You can store the email or other data here
                .claim("userId", userId) // Custom claim for userId
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Method to extract claims from the JWT token
    public Claims extractClaims(String token) {
        return Jwts.parser() // Use this method for older versions of jjwt
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // Method to extract the email from the JWT token
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Method to extract the user ID from the JWT token
    public String getUserIdFromToken(String token) {
        return extractClaims(token).get("userId", String.class); // Extract the userId from the token claims
    }

    // Method to check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Method to check if the token belongs to an admin
    public boolean isAdmin(String token) {
        return extractClaims(token).get("role").equals("admin");
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}