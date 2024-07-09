package com.ll.md0701.domain.member.member.service;

import com.ll.md0701.domain.member.member.entity.Member;
import com.ll.md0701.global.app.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(AppConfig.getJwtSecretKey().getBytes());
    }

    public String genToken(Member member, long expireSeconds) {
        Claims claims = Jwts.claims();
        claims.put("id", member.getId());
        claims.put("username", member.getUsername());
        claims.put("authorities", member.getAuthoritiesAsStringList());

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + 1000 * expireSeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String genAccessToken(Member member) {
        return genToken(member, AppConfig.getAccessTokenExpirationSec());
    }

    public Map<String, Object> getDataFrom(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Map.of(
                "id", claims.get("id", Integer.class),
                "username", claims.get("username", String.class),
                "authorities", claims.get("authorities", List.class)
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String genRefreshToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}