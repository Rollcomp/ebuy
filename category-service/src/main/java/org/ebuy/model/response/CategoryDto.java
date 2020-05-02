package org.ebuy.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Getter
@Setter
public class CategoryDto {

    private long id;
    private String name;
    private String beautifiedName;
    private long parentCategoryId;
    private Set<CategoryDto> subCategories;

}
