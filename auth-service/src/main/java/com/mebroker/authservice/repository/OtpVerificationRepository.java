package com.mebroker.authservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mebroker.authservice.domain.otp.OtpPurpose;
import com.mebroker.authservice.entity.OtpVerification;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> 
    findTopByDestinationAndPurposeOrderByExpiresAtDesc(
        String destination,
        OtpPurpose purpose
    );
   
}