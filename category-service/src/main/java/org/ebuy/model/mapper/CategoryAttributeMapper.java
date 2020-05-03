package org.ebuy.model.mapper;

import org.ebuy.model.CategoryAttribute;
import org.ebuy.model.request.CategoryAttributeRequest;
import org.ebuy.model.response.CategoryAttributeDto;
import org.ebuy.model.response.CategoryAttributesWithoutValuesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Mapper(componentModel = "spring", uses = {AttributeMapper.class, AttributeValueMapper.class})
public interface CategoryAttributeMapper {

    CategoryAttribute toCategoryAttribute(CategoryAttributeRequest categoryAttributeRequest);

    @Mapping(target = "attributeValues", source = "categoryAttribute.attribute.attributeValues")
    CategoryAttributeDto toCategoryAttributeDto(CategoryAttribute categoryAttribute);

    CategoryAttributesWithoutValuesDto toCategoryAttributeDtoWithoutValues(CategoryAttribute categoryAttribute);

    Set<CategoryAttributeDto> toCategoryAttributes(Set<CategoryAttribute> attributes);

}
