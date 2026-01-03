package com.mebroker.authservice.service.impl;

import com.mebroker.authservice.dto.request.LoginRequest;
import com.mebroker.authservice.dto.request.RefreshTokenRequest;
import com.mebroker.authservice.dto.response.AuthResponse;
import com.mebroker.authservice.entity.RefreshToken;
import com.mebroker.authservice.entity.User;
import com.mebroker.authservice.repository.RefreshTokenRepository;
import com.mebroker.authservice.repository.UserRepository;
import com.mebroker.authservice.security.JwtService;
import com.mebroker.authservice.service.AuthService;
import com.mebroker.common.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;
import java.util.Set;
import com.mebroker.authservice.dto.request.RegisterRequest;
import com.mebroker.authservice.entity.Role;
import com.mebroker.authservice.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepository userRepository,
                        RefreshTokenRepository refreshTokenRepository,
                        JwtService jwtService,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new UnauthorizedException("Refresh token expired");
        }

        String newAccessToken = jwtService.generateToken(refreshToken.getUser());

        return new AuthResponse(newAccessToken, refreshToken.getToken());
    }

    private String createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusSeconds(86400)); // 1 day
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role defaultRole = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new IllegalStateException("Default role CLIENT not found"));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(defaultRole))
                .build();

        userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }

}