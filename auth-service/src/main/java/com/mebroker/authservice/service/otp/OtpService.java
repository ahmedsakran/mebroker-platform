package com.mebroker.authservice.service.otp;

import com.mebroker.authservice.domain.otp.OtpChannel;
import com.mebroker.authservice.domain.otp.OtpPurpose;

public interface OtpService {

    void sendOtp(
            String destination,
            OtpChannel channel,
            OtpPurpose purpose
    );

    void verifyOtp(
            String destination,
            String otp,
            OtpPurpose purpose
    );
}