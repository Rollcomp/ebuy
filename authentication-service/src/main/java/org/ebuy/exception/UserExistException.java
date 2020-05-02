package org.ebuy.exception;

/**
 * Created by Ozgur Ustun on May, 2020
 */
public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
