package org.ebuy.exception;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(Long categoryId) {
        super("Category not found with category id : " + categoryId);
    }

}
