package org.ebuy.authservice.exception;

public class PasswordNotMatchingException extends Exception {
    public PasswordNotMatchingException(String message){
        super(message);
    }
}
