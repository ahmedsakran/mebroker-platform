package com.mebroker.authservice.service.impl;

import com.mebroker.authservice.entity.RefreshToken;
import com.mebroker.authservice.entity.User;
import com.mebroker.authservice.repository.RefreshTokenRepository;
import com.mebroker.authservice.security.JwtService;
import com.mebroker.authservice.service.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String createAccessToken(User user) {
        return jwtService.generateAccessToken(user);
    }

    @Override
    public String createRefreshToken(User user) {

        refreshTokenRepository.deactivateAllByUserId(user.getId());

        String token = jwtService.generateRefreshToken();

        RefreshToken refreshToken = RefreshToken.builder()
        .token(token)
        .user(user)
        .expiryDate(Instant.now().plus(30, ChronoUnit.DAYS))
        .build();

        refreshTokenRepository.save(refreshToken);

        return token;
    }

}