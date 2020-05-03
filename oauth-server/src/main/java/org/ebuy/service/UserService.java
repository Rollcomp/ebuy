package org.ebuy.service;

import org.ebuy.constant.Authority;
import org.ebuy.dto.ChangePasswordDto;
import org.ebuy.dto.MailDto;
import org.ebuy.dto.ResetPasswordDto;
import org.ebuy.dto.UserDto;
import org.ebuy.exception.TokenNotValidException;
import org.ebuy.exception.PasswordNotMatchingException;
import org.ebuy.exception.UserExistException;
import org.ebuy.kafka.Sender;
import org.ebuy.model.ConfirmationToken;
import org.ebuy.model.PasswordResetToken;
import org.ebuy.model.user.User;
import org.ebuy.repository.ConfirmationTokenRepository;
import org.ebuy.repository.PasswordResetTokenRepository;
import org.ebuy.repository.UserRepository;
import org.ebuy.util.TokenUtil;
import org.ebuy.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

/**
 * Created by Ozgur Ustun on May, 2020
 */

@Service
public class UserService {

    @Value("${spring.kafka.topic.userRegistered}")
    private String registerUserTopic;

    @Value("${spring.kafka.topic.userPassword}")
    private String resetPasswordTopic;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Sender sender;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository resetTokenRepository;

    private void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    private User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if (!user.isPresent()) {
            throw new UserExistException("User is not exist with this email: " + user.get().getEmail());
        }
        return user.get();
    }

    public Message<String> registerNewUserAccount(UserDto userDto) {
        //Check user already registered or not? If it is, throws UserExistsException
        userUtil.isEmailExist(userDto.getEmail());
        //TODO:Mapper will be added to dto-entity transform
        User registeredUser = new User();
        registeredUser.setEmail(userDto.getEmail());
        registeredUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        registeredUser.setEnabled(userDto.isEnabled());
        registeredUser.setAuthorities(new HashSet<Authority>(Collections.singleton(Authority.REGISTERED_USER)));

        ConfirmationToken token = new ConfirmationToken(registeredUser);
        tokenRepository.save(token);

        MailDto mailDto = new MailDto(registeredUser.getEmail(), token.getGeneratedConfirmationToken());
        sender.sendMail(mailDto, registerUserTopic);
        userRepository.save(registeredUser);

        return MessageBuilder.withPayload("User is registered").setHeader("userId", registeredUser.getId()).build();

    }

    public Message<String> confirmUserAccount(String confirmationToken) {
        Optional<ConfirmationToken> verificationToken = tokenRepository.findByGeneratedConfirmationToken(confirmationToken);

        if (!verificationToken.isPresent() || !tokenUtil.isValidConfirmationToken(verificationToken.get())) {
            throw new TokenNotValidException("Token is not valid!");
        }
            User user = findUserByEmail(verificationToken.get().getUser().getEmail());
            user.setEnabled(true);
            saveRegisteredUser(user);
            return MessageBuilder.withPayload("User account has confirmed").setHeader("userId", user.getId()).build();
    }

    public Message<String> resetUserPassword(String userEmail) {

        User user = findUserByEmail(userEmail);
        PasswordResetToken token = new PasswordResetToken(user);
        resetTokenRepository.save(token);
        MailDto mailDto = new MailDto(userEmail,token.getGeneratedPasswordResetToken());
        sender.sendMail(mailDto,resetPasswordTopic);
        return MessageBuilder.withPayload("Password reset mail has been sent").setHeader("userId", user.getId()).build();
    }

    public Message<String> confirmUserResetPassword(String resetPasswordToken, ResetPasswordDto passwordDto) throws PasswordNotMatchingException {
        Optional<PasswordResetToken> passwordResetToken = resetTokenRepository.findByGeneratedPasswordResetToken(resetPasswordToken);
        if(!passwordResetToken.isPresent() || !tokenUtil.isValidPasswordResetToken(passwordResetToken.get())){
            throw new TokenNotValidException("Token is not valid!");
        }
        User user = findUserByEmail(resetTokenRepository.findByGeneratedPasswordResetToken(resetPasswordToken).get().getUser().getEmail());
        if (!passwordDto.getNewPassword().equals(passwordDto.getNewPasswordAgain())) {
            throw new PasswordNotMatchingException("Password not matching!");
        }
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
        return MessageBuilder.withPayload("User password has resetted").setHeader("userId", user.getId()).build();
    }

    public Message<String> changeUserPassword(@RequestBody ChangePasswordDto changePasswordDto) throws PasswordNotMatchingException {
        //TODO: User should be logged in to change password. Control will be added
        User user = findUserByEmail(changePasswordDto.getUserEmail());
        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
            throw new PasswordNotMatchingException("Old password and new password not matching!");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return MessageBuilder.withPayload("User password has changed").setHeader("userId", user.getId()).build();
    }
}
