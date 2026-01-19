package com.mebroker.authservice.service;

import com.mebroker.authservice.dto.request.LoginRequest;
import com.mebroker.authservice.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}