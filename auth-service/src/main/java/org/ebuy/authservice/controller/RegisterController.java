package org.ebuy.authservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ebuy.authservice.dto.ChangePasswordDto;
import org.ebuy.authservice.dto.PasswordResetTokenDto;
import org.ebuy.authservice.dto.ResetPasswordDto;
import org.ebuy.authservice.dto.UserDto;
import org.ebuy.authservice.entity.ConfirmationToken;
import org.ebuy.authservice.entity.PasswordResetToken;
import org.ebuy.authservice.entity.User;
import org.ebuy.authservice.event.OnPasswordResetEvent;
import org.ebuy.authservice.event.OnRegistrationCompleteEvent;
import org.ebuy.authservice.exception.ExpiryTokenException;
import org.ebuy.authservice.exception.PasswordNotMatchingException;
import org.ebuy.authservice.exception.UserCredentialException;
import org.ebuy.authservice.exception.UserExistsException;
import org.ebuy.authservice.repository.ConfirmationTokenRepository;
import org.ebuy.authservice.repository.PasswordResetTokenRepository;
import org.ebuy.authservice.service.IUserService;
import org.ebuy.authservice.util.TokenUtil;

import javax.validation.Valid;

@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PasswordResetTokenRepository resetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDto userDto, BindingResult result) throws UserCredentialException, UserExistsException {
        log.debug("Registering user account with " + userDto.getEmail());

        /* Only userDto credential validation will be performed in controller to decrease controller complexity*/
        //Firstly check is this a valid userDto object or not
        if (result.hasErrors()) {
            throw new UserCredentialException("Please check user credentials! " +   userDto.toString());
        }

        User registeredUser = userService.registerNewUserAccount(userDto);
        //Event is published to trigger to send confirmation email.
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser));
        return new ResponseEntity("User registered", HttpStatus.OK);
    }

    @PostMapping(value = "/registrationConfirm")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) throws ExpiryTokenException {
        ConfirmationToken verificationToken = confirmationTokenRepository.findByGeneratedConfirmationToken(confirmationToken);
        if (verificationToken != null) {
            if (!tokenUtil.isValidConfirmationToken(verificationToken)) {
                throw new ExpiryTokenException("This activation link is not valid anymore.");
            }
            User user = userService.findUserByEmail(verificationToken.getUser().getEmail());
            user.setEnabled(true);
            userService.saveRegisteredUser(user);
            return new ResponseEntity("User registration confirmed", HttpStatus.OK);
        } else {
            return new ResponseEntity("Confirmation error", HttpStatus.BAD_REQUEST);
        }
        //TODO:After confirmation user should be redirected to home page(also should be logged in)
    }

    @PostMapping(value = "/user/resetPassword")
    public ResponseEntity<?> sendPasswordResetMail(@RequestBody PasswordResetTokenDto resetTokenDto) throws UserExistsException {
        User user = userService.findUserByEmail(resetTokenDto.getUserEmail());
        if (user == null) {
            throw new UserExistsException("User is not exist with this email: " + resetTokenDto.getUserEmail());
        }
        eventPublisher.publishEvent(new OnPasswordResetEvent(user));
        return ResponseEntity.ok("Password reset mail has been sent!");
    }

    @PostMapping(value = "/resetPassword")
    public ResponseEntity<?> resetUserPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDto passwordDto) throws PasswordNotMatchingException {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        PasswordResetToken passwordResetToken = resetTokenRepository.findByGeneratedPasswordResetToken(token);

        if (passwordResetToken != null && tokenUtil.isValidPasswordResetToken(passwordResetToken)) {
            userService.resetUserPassword(user, passwordDto);
            return ResponseEntity.ok("Password changed!");
        } else {
            throw new PasswordNotMatchingException("Password reset token is not valid!");
        }
    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<?> changeUserPassword(@RequestBody ChangePasswordDto changePasswordDto) throws PasswordNotMatchingException {
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            userService.changeUserPassword(user, changePasswordDto);
            return ResponseEntity.ok("Password changed!");
        } else {
            throw new PasswordNotMatchingException("Old password and new password not matching!");
        }
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello";
    }

}
