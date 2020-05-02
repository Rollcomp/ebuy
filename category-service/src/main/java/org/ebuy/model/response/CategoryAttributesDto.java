package org.ebuy.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Getter
@Setter
public class CategoryAttributesDto {

    private long id;
    private String name;
    private String beautifiedName;
    private Set<CategoryAttributeDto> categoryAttributes;

}
