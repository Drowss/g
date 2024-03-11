package com.oril.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) { //Get encrypted data from the token
        return Jwts.parser()
                .verifyWith((key))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isExpired(String token) { //Check if the token is expired
        return getClaims(token).getExpiration().before(new Date());
    }
}
