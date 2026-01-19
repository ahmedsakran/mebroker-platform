package com.mebroker.authservice.controller;

import com.mebroker.authservice.domain.otp.OtpChannel;
import com.mebroker.authservice.domain.otp.OtpPurpose;
import com.mebroker.authservice.dto.request.VerifyOtpRequest;
import com.mebroker.authservice.service.otp.OtpService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public void requestOtp(
            @RequestParam String destination,
            @RequestParam OtpChannel channel,
            @RequestParam OtpPurpose purpose
    ) {
        otpService.sendOtp(destination, channel, purpose);
    }

    @PostMapping("/verify")
     public ResponseEntity<?> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request
    ) {
        otpService.verifyOtp(
                request.getDestination(),
                request.getOtp(),
                request.getPurpose()
        );

        return ResponseEntity.ok("OTP verified successfully");
    }
}