package org.ebuy.exception;

/**
 * Created by Ozgur Ustun on May, 2020
 */
public class TokenNotValidException extends RuntimeException{
    public TokenNotValidException(String message){
        super(message);
    }
}
