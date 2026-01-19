package com.mebroker.authservice.service.otp;

import com.mebroker.authservice.domain.otp.OtpChannel;
import com.mebroker.authservice.domain.otp.OtpPurpose;
import com.mebroker.authservice.entity.OtpVerification;
import com.mebroker.authservice.repository.OtpVerificationRepository;
import com.mebroker.authservice.service.otp.sender.OtpSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpVerificationRepository repository;
    private final Map<String, OtpSender> senders;

    public OtpServiceImpl(
            OtpVerificationRepository repository,
            Map<String, OtpSender> senders
    ) {
        this.repository = repository;
        this.senders = senders;
    }

    @Override
    public void sendOtp(
            String destination,
            OtpChannel channel,
            OtpPurpose purpose
    ) {

        // ✅ 1. Defensive validation
        if (channel == null) {
            throw new IllegalArgumentException("OTP channel must not be null");
        }

        OtpSender sender = senders.get(channel.name());

        if (sender == null) {
            throw new IllegalStateException(
                    "No OtpSender found for channel: " + channel
            );
        }

        // ✅ 2. Generate OTP
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        // ✅ 3. Persist OTP
        OtpVerification entity = new OtpVerification();
        entity.setDestination(destination);
        entity.setOtpHash(otp); // TODO: hash later
        entity.setPurpose(purpose);
        entity.setChannel(channel);
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        entity.setVerified(false);

        repository.save(entity);

        // ✅ 4. Send OTP
        sender.send(destination, otp);
    }

    @Override
    public void verifyOtp(
            String destination,
            String otp,
            OtpPurpose purpose
    ) {

        OtpVerification entity = repository
                .findTopByDestinationAndPurposeOrderByExpiresAtDesc(
                        destination,
                        purpose
                )
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (entity.isVerified()) {
            throw new RuntimeException("OTP already used");
        }

        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!entity.getOtpHash().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        entity.setVerified(true);
        repository.save(entity);
    }
}
