package org.ebuy.authservice.service;

import org.ebuy.authservice.dto.ChangePasswordDto;
import org.ebuy.authservice.dto.ResetPasswordDto;
import org.ebuy.authservice.dto.UserDto;
import org.ebuy.authservice.entity.User;
import org.ebuy.authservice.exception.PasswordNotMatchingException;
import org.ebuy.authservice.exception.UserExistsException;

public interface IUserService {

    User registerNewUserAccount(UserDto userDto) throws UserExistsException;

//    User getUser(String confirmationToken);

    void saveRegisteredUser(User user);

//    void createConfirmationTokenForUser(User user, String token);

    User findUserByEmail(String email);

    User resetUserPassword(User user, ResetPasswordDto passwordDto) throws PasswordNotMatchingException;

    User changeUserPassword(User user, ChangePasswordDto changePasswordDto);

}

