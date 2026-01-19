package com.mebroker.authservice.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.mebroker.authservice.domain.otp.OtpChannel;
import com.mebroker.authservice.domain.otp.OtpPurpose;

@Entity
@Table(name = "otp_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination; // email or phone

    private String otpHash;

    @Enumerated(EnumType.STRING)
    private OtpPurpose purpose;

    @Enumerated(EnumType.STRING)
    private OtpChannel channel;

    private LocalDateTime expiresAt;

    private boolean verified;
}