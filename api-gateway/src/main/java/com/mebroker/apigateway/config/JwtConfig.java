package com.mebroker.apigateway.config;

import com.mebroker.common.security.JwtValidator;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    @Bean
    public SecretKey jwtSecretKey(
            @Value("${security.jwt.secret}") String secret
    ) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Bean
    public JwtValidator jwtValidator(SecretKey secretKey) {
        return new JwtValidator(secretKey);
    }
}
