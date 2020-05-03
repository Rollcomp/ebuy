package org.ebuy.util;

import org.ebuy.model.ConfirmationToken;
import org.ebuy.model.PasswordResetToken;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by Ozgur Ustun on May, 2020
 */
@Component
public class TokenUtil {

    private static final int expiryPeriod = 60 * 60 * 24;

    public boolean isValidConfirmationToken(ConfirmationToken confirmationToken) {
        LocalDateTime confirmationTime = LocalDateTime.now();
        LocalDateTime registeredTime = confirmationToken.getCreatedAt();
        Duration duration = Duration.between(confirmationTime, registeredTime);
        Long durationSeconds = duration.getSeconds();
        if (durationSeconds <= expiryPeriod) {
            confirmationToken.getUser().setEnabled(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidPasswordResetToken(PasswordResetToken passwordResetToken) {
        LocalDateTime passwordResetRequestTime = LocalDateTime.now();
        LocalDateTime registeredTime = passwordResetToken.getCreatedAt();
        Duration duration = Duration.between(passwordResetRequestTime, registeredTime);
        Long durationSeconds = duration.getSeconds();
        if (durationSeconds <= expiryPeriod) {
            return true;
        } else {
            return false;
        }
    }

}
