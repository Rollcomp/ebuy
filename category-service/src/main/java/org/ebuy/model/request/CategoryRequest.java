package org.ebuy.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Getter
@Setter
public class CategoryRequest {

    private String name;
    private long parentCategory;

}
