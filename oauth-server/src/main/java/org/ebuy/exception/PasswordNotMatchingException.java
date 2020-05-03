package org.ebuy.exception;

/**
 * Created by Ozgur Ustun on May, 2020
 */
public class PasswordNotMatchingException extends Exception {
    public PasswordNotMatchingException(String message){
        super(message);
    }
}