package org.ebuy.authservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.ebuy.authservice.dto.ChangePasswordDto;
import org.ebuy.authservice.dto.ResetPasswordDto;
import org.ebuy.authservice.dto.UserDto;
import org.ebuy.authservice.entity.User;
import org.ebuy.authservice.exception.PasswordNotMatchingException;
import org.ebuy.authservice.exception.UserExistsException;
import org.ebuy.authservice.repository.UserRepository;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserExistsException {
        //Check user already registered or not? If it is, throws UserExistsException

        if (isEmailExist(userDto.getEmail())) {
            throw new UserExistsException("User is already registered " + userDto.getEmail());
        }

        //User registered to database with bcrypted password
        User registeredUser = new User();
        registeredUser.setEmail(userDto.getEmail());
        registeredUser.setFirstName(userDto.getFirstName());
        registeredUser.setLastName(userDto.getLastName());
        registeredUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        registeredUser.setRoles(userDto.getRoles());
        return userRepository.save(registeredUser);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public User resetUserPassword(User user, ResetPasswordDto resetPasswordDto) throws PasswordNotMatchingException {
        if (!resetPasswordDto.getNewPassword().equals(resetPasswordDto.getNewPasswordAgain())) {
            throw new PasswordNotMatchingException("Password not matching!");
        }
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User changeUserPassword(User user, ChangePasswordDto changePasswordDto) {
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return user;
    }

    private boolean isEmailExist(String email) {
        if (userRepository.findByEmailIgnoreCase(email) != null) {
            return true;
        }
        return false;
    }

}
