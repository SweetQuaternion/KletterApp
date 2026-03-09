package com.dachpc.kletterapp.Security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateToken(String email) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getKey())
            .compact();
    }

    public String extractUserName(String token) {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        System.out.println("Validating token: " + token);
        System.out.println("User details: " + userDetails.getUsername());
        // Müssen checken:
        // 1. Ist das Token überhaupt gültig? (korrekt signiert, nicht manipuliert)
        // 2. Ist das Token noch nicht abgelaufen? 
        // 3. Gehört das Token zu dem Nutzer, der sich einloggen will?
        try {
            boolean isNotExpired = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .after(new Date());
            System.out.println("Token nicht abgelaufen: " + isNotExpired);
            System.out.println("Nutzer ist korrekt: " + extractUserName(token).equals(userDetails.getUsername()));
            System.out.println("Also Token gültig: " + (extractUserName(token).equals(userDetails.getUsername()) && isNotExpired));
            return extractUserName(token).equals(userDetails.getUsername()) && isNotExpired;
        } catch (JwtException e) {
            System.out.println("Fehler beim Validieren des Tokens: " + e.getMessage());
            return false; // Hier wird irgendwas schiefgelaufen sein, besser nicht erlauben
        }
    }
}