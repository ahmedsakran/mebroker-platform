package com.mebroker.authservice.dto.request;

import com.mebroker.authservice.domain.otp.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {

    @NotBlank
    private String destination; // email or phone

    @NotBlank
    private String otp;

    private OtpPurpose purpose; // REGISTER, RESET_PASSWORD
}