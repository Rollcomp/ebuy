package org.ebuy.service;

import org.ebuy.constant.Authority;
import org.ebuy.dto.MailDto;
import org.ebuy.dto.UserDto;
import org.ebuy.exception.UserExistException;
import org.ebuy.kafka.Sender;
import org.ebuy.model.ConfirmationToken;
import org.ebuy.model.user.User;
import org.ebuy.repository.ConfirmationTokenRepository;
import org.ebuy.repository.UserRepository;
import org.ebuy.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Ozgur Ustun on May, 2020
 */

@Service
public class UserService {

    @Value("${spring.kafka.topic.userRegistered}")
    private String USER_REGISTERED_TOPIC;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Sender sender;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public User registerNewUserAccount(UserDto userDto) {

        //Check user already registered or not? If it is, throws UserExistsException
        if (userUtil.isEmailExist(userDto.getEmail())) {
            throw new UserExistException("User is already registered " + userDto.getEmail());
        }

        User registeredUser = new User();
        registeredUser.setEmail(userDto.getEmail());
        registeredUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        registeredUser.setEnabled(userDto.isEnabled());
        registeredUser.setAuthorities(new HashSet<Authority>(Collections.singleton(Authority.REGISTERED_USER)));

        ConfirmationToken token = new ConfirmationToken(registeredUser);
        tokenRepository.save(token);

        MailDto mailDto = new MailDto(registeredUser.getEmail(), token.getGeneratedConfirmationToken());
        sender.send(mailDto);

        return userRepository.save(registeredUser);

    }

}
