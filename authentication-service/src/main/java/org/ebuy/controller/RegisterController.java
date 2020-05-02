package org.ebuy.controller;

import org.ebuy.dto.UserDto;
import org.ebuy.exception.ExpiryTokenException;
import org.ebuy.model.ConfirmationToken;
import org.ebuy.model.user.User;
import org.ebuy.repository.ConfirmationTokenRepository;
import org.ebuy.service.UserService;
import org.ebuy.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ozgur Ustun on May, 2020
 */

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/user")
    public Message<String> registerUser(@RequestBody UserDto userDto) {

        User registeredUser = userService.registerNewUserAccount(userDto);
        //TODO: Activation mail should be triggered with kafka
        return MessageBuilder.withPayload("User is registered").setHeader("userId", registeredUser.getId()).build();

    }

    @PostMapping("/confirm")
    public Message<String> confirmUserAccount(@RequestParam("t") String confirmationToken) {
        ConfirmationToken verificationToken = tokenRepository.findByGeneratedConfirmationToken(confirmationToken);

        User user = null;
        if (verificationToken != null) {
            if (!tokenUtil.isValidConfirmationToken(verificationToken)) {
                throw new ExpiryTokenException("This activation link is not valid anymore.");
            }
            user = userService.findUserByEmail(verificationToken.getUser().getEmail());
            user.setEnabled(true);
            userService.saveRegisteredUser(user);
            return MessageBuilder.withPayload("User account has confirmed").setHeader("userId", user.getId()).build();
        } else {
            return MessageBuilder.withPayload("User account has not confirmed").setHeader("userId", user.getId()).build();
        }
    }
}
