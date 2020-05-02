package org.ebuy.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Getter
@Setter
public class CategoryAttributeRequest {

    private long attributeId;
    private boolean required;
    private boolean allowCustom;

}
