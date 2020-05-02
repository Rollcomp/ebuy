package org.ebuy.exception;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public class AttributeNotFoundException extends NotFoundException {

    public AttributeNotFoundException(Long categoryId) {
        super("Attribute not found with attribute id : " + categoryId);
    }

}
