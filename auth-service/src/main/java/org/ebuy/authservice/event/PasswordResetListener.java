package org.ebuy.authservice.event;

import org.ebuy.authservice.entity.PasswordResetToken;
import org.ebuy.authservice.entity.User;
import org.ebuy.authservice.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {

    @Autowired
    private PasswordResetTokenRepository resetTokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnPasswordResetEvent event) {
        this.confirmPasswordReset(event);
    }

    private void confirmPasswordReset(OnPasswordResetEvent event) {
        User user = event.getUser();
        PasswordResetToken token = new PasswordResetToken(user);
        resetTokenRepository.save(token);
        SimpleMailMessage email = generateMailMessage(event, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage generateMailMessage(ApplicationEvent event, User user, PasswordResetToken token) {
        String sendTo = user.getEmail();
        String subject = "Please click the link to reset your password";
        String confirmationUrl = "http://localhost:8082/resetPassword?token=" + token.getGeneratedPasswordResetToken();
        String message = "To reset your password, please click the link "  + confirmationUrl;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(sendTo);
        email.setSubject(subject);
        email.setText(message);
        return email;
    }
}
