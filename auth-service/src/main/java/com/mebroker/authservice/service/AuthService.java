package com.mebroker.authservice.service;

import com.mebroker.authservice.dto.request.LoginRequest;
import com.mebroker.authservice.dto.request.RefreshTokenRequest;
import com.mebroker.authservice.dto.request.RegisterRequest;
import com.mebroker.authservice.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    AuthResponse register(RegisterRequest request);
}