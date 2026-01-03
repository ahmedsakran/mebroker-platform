package com.mebroker.authservice.controller;

import com.mebroker.authservice.dto.request.LoginRequest;
import com.mebroker.authservice.dto.request.RefreshTokenRequest;
import com.mebroker.authservice.dto.response.AuthResponse;
import com.mebroker.authservice.service.AuthService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.mebroker.authservice.dto.request.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

}
