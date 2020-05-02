package org.ebuy.mailservice.service;

import org.ebuy.mailservice.dto.ReceiveMailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by Ozgur Ustun on May, 2020
 */

@Service
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendRegisterMail(ReceiveMailDto receiveMailDto) {
        SimpleMailMessage email = generateRegisterMailMessage(receiveMailDto.getEmail(), receiveMailDto.getToken());
        emailSender.send(email);
    }

    private SimpleMailMessage generateRegisterMailMessage(String userEmail, String token) {
        String sendTo = userEmail;
        String subject = "Please verify your email address...";
        //TODO: Refactor mail link
        String confirmationUrl = "http://localhost:8080/registerservice/api/registrationConfirm?t=" + token;
        String message = "To confirm registration, please click the link: "  + confirmationUrl;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(sendTo);
        email.setSubject(subject);
        email.setText(message);
        return email;
    }
}
