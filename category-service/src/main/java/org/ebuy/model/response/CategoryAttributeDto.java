package org.ebuy.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Getter
@Setter
public class CategoryAttributeDto {

    private AttributeDto attribute;
    private boolean required;
    private boolean allowCustom;
    private Set<AttributeValueDto> attributeValues;

}
