package org.ebuy.model.mapper;

import org.ebuy.model.Category;
import org.ebuy.model.request.CategoryRequest;
import org.ebuy.model.response.CategoryAttributesDto;
import org.ebuy.model.response.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Mapper(componentModel = "spring", uses = {CategoryAttributeMapper.class})
public interface CategoryMapper {

    Category toCategory(CategoryRequest categoryRequest);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtoList(List<Category> categoryList);

    CategoryAttributesDto toCategoryAttributesDto(Category category);

}
