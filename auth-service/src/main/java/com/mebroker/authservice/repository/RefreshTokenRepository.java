package com.mebroker.authservice.repository;

import com.mebroker.authservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenAndIsActiveTrue(String token);

    @Modifying
    @Query("update RefreshToken r set r.isActive = false where r.user.id = :userId")
    void deactivateAllByUserId(Long userId);
}
