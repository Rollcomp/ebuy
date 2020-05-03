package org.ebuy.util;

import org.ebuy.model.user.User;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Created by Ozgur Ustun on May, 2020
 */
@Component
public class TokenUtil {

    private static final int expiryPeriod = 60 * 60 * 24;

    public boolean isValidConfirmationToken(User user) {
        LocalDateTime confirmationTime = LocalDateTime.now();
        LocalDateTime registeredTime = user.getActivationKeycreatedTime();
        Duration duration = Duration.between(confirmationTime, registeredTime);
        Long durationSeconds = duration.getSeconds();
        if (durationSeconds <= expiryPeriod) {
            user.setEnabled(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidPasswordResetToken(User user) {
        LocalDateTime passwordResetRequestTime = LocalDateTime.now();
        LocalDateTime registeredTime = user.getResetPasswordKeycreatedTime();
        Duration duration = Duration.between(passwordResetRequestTime, registeredTime);
        Long durationSeconds = duration.getSeconds();
        if (durationSeconds <= expiryPeriod) {
            return true;
        } else {
            return false;
        }
    }

}
