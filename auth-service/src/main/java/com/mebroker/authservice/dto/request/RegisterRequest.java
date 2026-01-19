package com.mebroker.authservice.dto.request;

import java.util.Set;
import com.mebroker.authservice.domain.otp.OtpChannel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String username;

    @Email
    private String email;

    private String phone;

    @NotBlank
    private String password;

    @NotNull
    private OtpChannel channel; // EMAIL or SMS

    private Set<String> roles; // e.g. ["CLIENT", "BROKER"]
}