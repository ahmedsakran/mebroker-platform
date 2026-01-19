package com.mebroker.authservice.service.otp.sender;

public interface OtpSender {
    void send(String destination, String otp);
}