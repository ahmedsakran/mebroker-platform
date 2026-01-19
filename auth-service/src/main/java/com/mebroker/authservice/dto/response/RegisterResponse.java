package com.mebroker.authservice.dto.response;

import com.mebroker.authservice.domain.otp.OtpChannel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

    @NotBlank
    private String username;

    @Email
    private String email;

    private String phone;

    @NotBlank
    private String password;

    private OtpChannel channel; // EMAIL or SMS
}