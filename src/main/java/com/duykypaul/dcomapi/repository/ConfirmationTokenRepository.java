package com.duykypaul.dcomapi.repository;

import com.duykypaul.dcomapi.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);

    Optional<ConfirmationToken> findByConfirmationTokenAndExpirationDateGreaterThanEqual(String confirmationToken, Date datetimeVerify);
}
