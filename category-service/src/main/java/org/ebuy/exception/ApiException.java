package org.ebuy.exception;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

}