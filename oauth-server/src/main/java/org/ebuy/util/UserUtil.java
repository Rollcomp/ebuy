package org.ebuy.util;

import org.ebuy.exception.UserExistException;
import org.ebuy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Ozgur Ustun on May, 2020
 */
@Component
public class UserUtil {

    @Autowired
    private UserRepository userRepository;

    public boolean isEmailExist(String email) {
        if (userRepository.findByEmailIgnoreCase(email) != null) {
            throw new UserExistException("User is already registered " + email);
        }
        return false;
    }
}
