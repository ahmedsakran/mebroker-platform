package com.mebroker.authservice.service.impl;

import com.mebroker.authservice.dto.request.LoginRequest;
import com.mebroker.authservice.dto.request.RegisterRequest;
import com.mebroker.authservice.dto.response.AuthResponse;
import com.mebroker.authservice.entity.SystemRole;
import com.mebroker.authservice.entity.User;
import com.mebroker.authservice.entity.UserSystemRole;
import com.mebroker.authservice.repository.SystemRoleRepository;
import com.mebroker.authservice.repository.UserRepository;
import com.mebroker.authservice.service.AuthService;
import com.mebroker.authservice.service.TokenService;
import com.mebroker.common.exception.BadRequestException;
import com.mebroker.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException(
                        "INVALID_CREDENTIALS",
                        "Invalid username or password"
                ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(
                    "INVALID_CREDENTIALS",
                    "Invalid username or password"
            );
        }

        return AuthResponse.builder()
                .accessToken(tokenService.createAccessToken(user))
                .refreshToken(tokenService.createRefreshToken(user))
                .build();
    }

//     @Override
// public AuthResponse register(RegisterRequest request) {

//     if (userRepository.existsByUsername(request.getUsername())) {
//         throw new BadRequestException("USERNAME_EXISTS", "Username already exists");
//     }

//     if (userRepository.existsByEmail(request.getEmail())) {
//         throw new BadRequestException("EMAIL_EXISTS", "Email already exists");
//     }

//     SystemRole clientRole = systemRoleRepository
//             .findByRoleTitle("Client")
//             .orElseThrow(() ->
//                     new RuntimeException("Default role CLIENT not found"));

//     // 1️⃣ إنشاء المستخدم
//     User user = User.builder()
//             .username(request.getUsername())
//             .email(request.getEmail())
//             .password(passwordEncoder.encode(request.getPassword()))
//             .isActive(true)
//             .build();

//     // 2️⃣ إنشاء الربط User ↔ Role
//     UserSystemRole userRole = UserSystemRole.builder()
//             .user(user)
//             .systemRole(clientRole)
//             .build();

//     // 3️⃣ ربط الأدوار بالمستخدم
//     user.setUserRoles(Set.of(userRole));

//     // 4️⃣ حفظ المستخدم (Cascade سيحفظ UserSystemRole)
//     userRepository.save(user);

//     return AuthResponse.builder()
//             .accessToken(tokenService.createAccessToken(user))
//             .refreshToken(tokenService.createRefreshToken(user))
//             .build();
// }


}
