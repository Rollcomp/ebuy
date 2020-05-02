package org.ebuy.exception;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public class BeautifiedNameException extends ApiException {

    public BeautifiedNameException(String beautifiedName) {
        super("Categories cannot have same beautified name : " + beautifiedName);
    }

}
