package com.mebroker.authservice.service.otp.sender;

import org.springframework.stereotype.Component;

@Component("SMS")
public class SmsOtpSender implements OtpSender {

    @Override
    public void send(String destination, String otp) {
        // TODO integrate SMS provider
        System.out.println("Sending SMS OTP to " + destination + " OTP: " + otp);
    }
}
