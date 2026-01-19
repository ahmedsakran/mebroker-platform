package com.mebroker.authservice.controller;

import com.mebroker.authservice.dto.request.LoginRequest;
import com.mebroker.authservice.dto.response.AuthResponse;
import com.mebroker.authservice.service.AuthService;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import com.mebroker.authservice.dto.request.RefreshTokenRequest;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import com.mebroker.authservice.dto.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

}