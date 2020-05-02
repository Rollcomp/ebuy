package org.ebuy.model.mapper;

import org.ebuy.model.Attribute;
import org.ebuy.model.response.AttributeDto;
import org.mapstruct.Mapper;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Mapper(componentModel = "spring")
public interface AttributeMapper {

    AttributeDto toAttributeDto(Attribute attribute);

}
