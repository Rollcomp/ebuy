package org.ebuy.exception;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public class ParentCategoryNotFoundException extends NotFoundException {

    public ParentCategoryNotFoundException(Long categoryId) {
        super("Parent category not found with category id : " + categoryId);
    }

}