package org.ebuy.controller;

import org.ebuy.dto.ChangePasswordDto;
import org.ebuy.dto.ResetPasswordDto;
import org.ebuy.dto.UserDto;
import org.ebuy.exception.PasswordNotMatchingException;
import org.ebuy.exception.UserExistException;
import org.ebuy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ozgur Ustun on May, 2020
 */

@RestController
@RequestMapping("/api")
public class UserRegisterController {

    @Autowired
    private UserService userService;


    @PostMapping("/registerUser")
    public Message<String> registerUser(@RequestBody UserDto userDto) {
        return userService.registerNewUserAccount(userDto);
    }

    @PostMapping("/confirmUser")
    public Message<String> confirmUserAccount(@RequestParam("t") String confirmationToken) {
        return userService.confirmUserAccount(confirmationToken);
    }

    @PostMapping("/resetPassword")
    public Message<String> sendPasswordResetMail(@RequestBody String userEmail) throws UserExistException {
        return userService.resetUserPassword(userEmail);
    }

    @PostMapping("/resetPassword")
    public Message<String> resetUserPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDto passwordDto) throws PasswordNotMatchingException {
        return userService.confirmUserResetPassword(token, passwordDto);
    }

    @PostMapping("/changePassword")
    public Message<String> changeUserPassword(@RequestBody ChangePasswordDto changePasswordDto) throws PasswordNotMatchingException {
        return userService.changeUserPassword(changePasswordDto);
    }
}
