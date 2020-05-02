package org.ebuy.authservice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.ebuy.authservice.entity.ConfirmationToken;
import org.ebuy.authservice.entity.User;
import org.ebuy.authservice.repository.ConfirmationTokenRepository;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        ConfirmationToken token = new ConfirmationToken(user);
        tokenRepository.save(token);
        SimpleMailMessage email = generateMailMessage(user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage generateMailMessage(User user, ConfirmationToken token) {
        String sendTo = user.getEmail();
        String subject = "Please verify your email address...";
        String confirmationUrl = "http://localhost:8082/registrationConfirm?token=" + token.getGeneratedConfirmationToken();
        String message = "To confirm registration, please click the link: "  + confirmationUrl;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(sendTo);
        email.setSubject(subject);
        email.setText(message);
        return email;
    }
}
