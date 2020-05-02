package org.ebuy.model.category.request;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public class CategoryRequest {

    private String name;
    private long parentCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(long parentCategory) {
        this.parentCategory = parentCategory;
    }

}
