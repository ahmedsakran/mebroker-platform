package com.mebroker.common.security;

import com.mebroker.common.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

public class JwtValidator {

    private final Key signingKey;

    public JwtValidator(String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtClaims validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(cleanToken(token))
                    .getBody();

            String userId = claims.get(JwtConstants.CLAIM_USER_ID, String.class);
            List<String> roles = claims.get(JwtConstants.CLAIM_ROLES, List.class);

            return new JwtClaims(userId, roles);

        } catch (Exception ex) {
            throw new UnauthorizedException("Invalid or expired JWT token", ex);
        }
    }

    private String cleanToken(String token) {
        if (token.startsWith(JwtConstants.TOKEN_PREFIX)) {
            return token.substring(JwtConstants.TOKEN_PREFIX.length());
        }
        return token;
    }
}