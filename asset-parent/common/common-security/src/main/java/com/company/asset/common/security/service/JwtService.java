package com.company.asset.common.security.service;

import com.company.asset.common.core.constant.GlobalConstants;
import com.company.asset.common.security.dto.JwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT service
 */
@Slf4j
@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtService() {
        this.secretKey = Keys.hmacShaKeyFor(GlobalConstants.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = GlobalConstants.ACCESS_TOKEN_EXPIRATION;
        this.refreshTokenExpiration = GlobalConstants.REFRESH_TOKEN_EXPIRATION;
    }

    /**
     * Generate access token
     */
    public String generateAccessToken(Long userId, String username, Long deptId, String roles) {
        return generateToken(userId, username, deptId, roles, "access", accessTokenExpiration);
    }

    /**
     * Generate refresh token
     */
    public String generateRefreshToken(Long userId, String username, Long deptId, String roles) {
        return generateToken(userId, username, deptId, roles, "refresh", refreshTokenExpiration);
    }

    /**
     * Generate token
     */
    private String generateToken(Long userId, String username, Long deptId, String roles, String tokenType, long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("deptId", deptId);
        claims.put("roles", roles);
        claims.put("tokenType", tokenType);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Parse token
     */
    public JwtPayload parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            JwtPayload payload = new JwtPayload();
            payload.setUserId(((Number) claims.get("userId")).longValue());
            payload.setUsername(claims.get("username", String.class));
            payload.setDeptId(((Number) claims.get("deptId")).longValue());
            payload.setRoles(claims.get("roles", String.class));
            payload.setTokenType(claims.get("tokenType", String.class));
            payload.setIat(claims.getIssuedAt().getTime());
            payload.setExp(claims.getExpiration().getTime());
            return payload;
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}", e.getMessage());
            throw new RuntimeException("Token expired");
        } catch (MalformedJwtException e) {
            log.warn("Invalid token format: {}", e.getMessage());
            throw new RuntimeException("Invalid token");
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported token: {}", e.getMessage());
            throw new RuntimeException("Unsupported token");
        } catch (SignatureException e) {
            log.warn("Invalid token signature: {}", e.getMessage());
            throw new RuntimeException("Invalid token signature");
        } catch (Exception e) {
            log.warn("Token parse error: {}", e.getMessage());
            throw new RuntimeException("Token parse error");
        }
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Get token from header
     */
    public String getTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith(GlobalConstants.TOKEN_PREFIX)) {
            return authHeader.substring(GlobalConstants.TOKEN_PREFIX.length());
        }
        return null;
    }
}
