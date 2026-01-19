package com.mebroker.authservice.service.otp.sender;

import org.springframework.stereotype.Component;

@Component("EMAIL")
public class EmailOtpSender implements OtpSender {

    @Override
    public void send(String destination, String otp) {
        // TODO integrate real email provider
        System.out.println("Sending EMAIL OTP to " + destination + " OTP: " + otp);
    }
}