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
import org.ebuy.model.user.User;
import org.ebuy.repository.UserRepository;
import org.ebuy.util.TokenUtil;
import org.ebuy.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

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
        registeredUser.setEnabled(false);
        registeredUser.setAuthorities(new HashSet<Authority>(Collections.singleton(Authority.REGISTERED_USER)));
        registeredUser.setActivationKey(UUID.randomUUID().toString());
        registeredUser.setActivationKeycreatedTime(LocalDateTime.now());
        userRepository.save(registeredUser);
        MailDto mailDto = new MailDto(registeredUser.getEmail(), registeredUser.getActivationKey());

        sender.sendMail(mailDto, registerUserTopic);
        userRepository.save(registeredUser);

        return MessageBuilder.withPayload("User is registered").setHeader("userId", registeredUser.getId()).build();

    }

    public Message<String> confirmUserAccount(String confirmationToken) {
        Optional<User> user = userRepository.findByActivationKey(confirmationToken);

        if (!user.isPresent() || !tokenUtil.isValidConfirmationToken(user.get())) {
            throw new TokenNotValidException("Token is not valid!");
        }
            user.get().setEnabled(true);
            saveRegisteredUser(user.get());
            return MessageBuilder.withPayload("User account has confirmed").setHeader("userId", user.get().getId()).build();
    }

    public Message<String> resetUserPassword(String userEmail) {

        User user = findUserByEmail(userEmail);
        user.setResetPasswordKey(UUID.randomUUID().toString());
        user.setResetPasswordKeycreatedTime(LocalDateTime.now());
        userRepository.save(user);
        MailDto mailDto = new MailDto(userEmail,user.getResetPasswordKey());
        sender.sendMail(mailDto,resetPasswordTopic);
        return MessageBuilder.withPayload("Password reset mail has been sent").setHeader("userId", user.getId()).build();
    }

    public Message<String> confirmUserResetPassword(String resetPasswordToken, ResetPasswordDto passwordDto) throws PasswordNotMatchingException {
        Optional<User> user = userRepository.findByResetPasswordKey(resetPasswordToken);
        if(!user.isPresent() || !tokenUtil.isValidPasswordResetToken(user.get())){
            throw new TokenNotValidException("Token is not valid!");
        }
        if (!passwordDto.getNewPassword().equals(passwordDto.getNewPasswordAgain())) {
            throw new PasswordNotMatchingException("Password not matching!");
        }
        user.get().setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user.get());
        return MessageBuilder.withPayload("User password has resetted").setHeader("userId", user.get().getId()).build();
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
