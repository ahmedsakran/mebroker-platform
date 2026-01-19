package com.mebroker.authservice.service.register;

import com.mebroker.authservice.domain.otp.OtpChannel;
import com.mebroker.authservice.domain.otp.OtpPurpose;
import com.mebroker.authservice.dto.request.RegisterRequest;
import com.mebroker.authservice.entity.SystemRole;
import com.mebroker.authservice.entity.User;
import com.mebroker.authservice.entity.UserSystemRole;
import com.mebroker.authservice.repository.SystemRoleRepository;
import com.mebroker.authservice.repository.UserRepository;
import com.mebroker.authservice.repository.UserSystemRoleRepository;
import com.mebroker.authservice.service.otp.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final SystemRoleRepository systemRoleRepository;
    private final UserSystemRoleRepository userSystemRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Override
    public void register(RegisterRequest request) {

        // 1️⃣ Validate OTP channel
        if (request.getChannel() == OtpChannel.EMAIL && request.getEmail() == null) {
            throw new RuntimeException("Email is required for EMAIL OTP");
        }

        if (request.getChannel() == OtpChannel.SMS && request.getPhone() == null) {
            throw new RuntimeException("Phone is required for SMS OTP");
        }

        // 2️⃣ Create user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(false) // will be activated after OTP verify
                .build();

        userRepository.save(user);

        // 3️⃣ Attach roles
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleTitle : request.getRoles()) {

                SystemRole role = systemRoleRepository
                        .findByTitle(roleTitle)
                        .orElseThrow(() ->
                                new RuntimeException("Role not found: " + roleTitle)
                        );

                UserSystemRole userRole = UserSystemRole.builder()
                        .user(user)
                        .systemRole(role)
                        .build();

                userSystemRoleRepository.save(userRole);
            }
        }

        // 4️⃣ Send OTP
        String destination =
                request.getChannel() == OtpChannel.EMAIL
                        ? request.getEmail()
                        : request.getPhone();

        otpService.sendOtp(
                destination,
                request.getChannel(),
                OtpPurpose.REGISTER
        );
    }
}