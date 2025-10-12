package com.example.component.project2.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.component.project2.entity.User;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
  @Value("${application.security.jwt.secret-key}") private String secret;
  @Value("${application.security.jwt.expiration}") private long expiration;
  @Value("${application.security.jwt.refresh-token.expiration}") private long refreshExpiration;

  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  public String generateToken(User user) {
    return buildToken(user.getUsername(), expiration);
  }

  public String generateRefreshToken(User user) {
    return buildToken(user.getUsername(), refreshExpiration);
  }

  public boolean isTokenValid(String token, User user) {
    final String username = extractUsername(token);
    return (username.equals(user.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }

  private String buildToken(String subject, long expMs) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + expMs);
    return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token).getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
