package org.ebuy.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Getter
@Setter
public class CategoryAttributesWithoutValuesDto {

    private Long id;
    private String name;
    private String beautifiedName;

}
