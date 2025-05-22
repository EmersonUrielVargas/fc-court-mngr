package com.foodcourt.court.infrastructure.security.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    @Spy
    private JwtService jwtService;

    private SecretKey testSigningKey;

    @BeforeEach
    void setUp() {
        String testSecretKeyBase64 = "fdt5NjM4MjY0ZTMxMzAzNTMxMzUzMzM0MzUzNjMwMzgzMTM0MzUzMjMwMzY=";
        ReflectionTestUtils.setField(jwtService, "secretKey", testSecretKeyBase64);

        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(testSecretKeyBase64);
        testSigningKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateTestToken(String subject, long expirationMillis, Map<String, Object> claims) {
        Date createdDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(createdDate.getTime() + expirationMillis);
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(createdDate)
                .expiration(expirationDate)
                .signWith(testSigningKey)
                .compact();
    }

    @Test
    void extractUserEmail() {
        String userEmail = "test@example.com";
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 12L);
        claims.put("role","ADMIN");
        String token = generateTestToken(userEmail, TimeUnit.MINUTES.toMillis(5), claims);

        String extractedEmail = jwtService.extractUserEmail(token);

        assertEquals(userEmail, extractedEmail);
    }

    @Test
    void isTokenValidAndNotExpired() {
        String userEmail = "test@example.com";
        String token = generateTestToken(userEmail, TimeUnit.MINUTES.toMillis(5), new HashMap<>());

        boolean isValid = jwtService.isTokenValid(token);

        assertTrue(isValid);
    }

    @Test
    void isTokenValidAndExpired() {
        String userEmail = "test@example.com";
        String token = generateTestToken(userEmail, -TimeUnit.MINUTES.toMillis(5), new HashMap<>());

        assertThrows(ExpiredJwtException.class, ()-> jwtService.isTokenValid(token));
    }

    @Test
    void extractClaim() {
        String userEmail = "user@example.com";
        String customClaimKey = "userId";
        Long expectedUserId = 123L;
        Map<String, Object> claims = new HashMap<>();
        claims.put(customClaimKey, expectedUserId);

        String token = generateTestToken(userEmail, TimeUnit.MINUTES.toMillis(30), claims);

        Long actualUserId = jwtService.extractClaim(token, c -> c.get(customClaimKey, Long.class));

        assertEquals(expectedUserId, actualUserId);
    }
}