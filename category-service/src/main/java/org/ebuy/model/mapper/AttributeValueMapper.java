package org.ebuy.model.mapper;

import org.ebuy.model.AttributeValue;
import org.ebuy.model.response.AttributeValueDto;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Mapper(componentModel = "spring")
public interface AttributeValueMapper {

    Set<AttributeValueDto> toAttributeValues(Set<AttributeValue> attributeValues);

}
