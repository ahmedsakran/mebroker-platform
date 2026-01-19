package com.mebroker.authservice.security;

import com.mebroker.authservice.entity.User;
import com.mebroker.common.security.JwtConstants;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import com.mebroker.common.security.JwtClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKey secretKey;   // ✅ من JwtConfig

    @Value("${security.jwt.access-expiration}")
    private long accessTokenExpiration;

    /**
     * Generate ACCESS TOKEN (JWT)
     */
     public String generateAccessToken(User user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(JwtClaims.USER_ID, user.getId())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + accessTokenExpiration)
                )
                .signWith(secretKey)
                .compact();
    }

    /**
     * Generate REFRESH TOKEN (Random UUID)
    */
    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }
}