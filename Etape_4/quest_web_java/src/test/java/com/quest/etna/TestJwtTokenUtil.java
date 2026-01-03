package com.quest.etna;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class TestJwtTokenUtil {

    private final String testSecretKey = "IyNldG5hX3F1ZXN0XzIwMjMjI2V0bmFfcXVlc3RfMjAyMyMjZXRuYV9xdWVzdF8yMDIzIyM=";
    private final long testJwtExpiration = 3600 * 24; // Durée d'expiration personnalisée pour les tests

    public String generateTestToken(String username, Map<String, Object> extraClaims) {
        return buildToken(extraClaims, username, testJwtExpiration, testSecretKey);
    }

    // Autres méthodes nécessaires pour la construction du token (comme dans JwtTokenUtil)
    // ...

    private String buildToken(Map<String, Object> extraClaims, String subject, long expiration, String secretKey) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
