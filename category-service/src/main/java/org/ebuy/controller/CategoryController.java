package org.ebuy.controller;

import org.ebuy.model.Category;
import org.ebuy.model.CategoryAttribute;
import org.ebuy.model.mapper.CategoryAttributeMapper;
import org.ebuy.model.mapper.CategoryMapper;
import org.ebuy.model.request.CategoryAttributeRequest;
import org.ebuy.model.request.CategoryRequest;
import org.ebuy.model.response.CategoryAttributeDto;
import org.ebuy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;
    private CategoryMapper categoryMapper;
    private CategoryAttributeMapper categoryAttributeMapper;

    @Autowired
    private CategoryController(CategoryService categoryService,
                               CategoryMapper categoryMapper,
                               CategoryAttributeMapper categoryAttributeMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.categoryAttributeMapper = categoryAttributeMapper;
    }

    @GetMapping
    public ResponseEntity<?> getCategories() {
        List<Category> categoryList = categoryService.findAllCategories();
        return ResponseEntity.ok(categoryMapper.toCategoryDtoList(categoryList));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable long categoryId) {
        Category category = categoryService.findCategory(categoryId);
        return ResponseEntity.ok(categoryMapper.toCategoryDto(category));
    }

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        Category savedCategory = categoryService.createCategory(categoryMapper.toCategory(request), request.getParentCategory());
        return ResponseEntity.ok(categoryMapper.toCategoryDto(savedCategory));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable long categoryId, @RequestBody CategoryRequest request) {
        Category updatedCategory = categoryMapper.toCategory(request);
        Category result = categoryService.updateCategory(categoryId, updatedCategory, request.getParentCategory());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping("/{categoryId}/attributes")
    public ResponseEntity<?> getCategoryAttributes(@PathVariable long categoryId) {
        Category category = categoryService.findCategory(categoryId);
        return ResponseEntity.ok(categoryMapper.toCategoryAttributesDto(category));
    }

    @PostMapping("/{categoryId}/attributes")
    public ResponseEntity<?> addAttributeToCategory(@PathVariable long categoryId, @RequestBody CategoryAttributeRequest request) {
        CategoryAttribute categoryAttribute = categoryAttributeMapper.toCategoryAttribute(request);
        CategoryAttribute result = categoryService.addAttributeToCategory(categoryId, categoryAttribute, request.getAttributeId());
        return ResponseEntity.ok(categoryAttributeMapper.toCategoryAttributeDtoWithoutValues(result));
    }

    @PutMapping("/{categoryId}/attributes")
    public ResponseEntity<?> updateAttributeForCategory(@PathVariable long categoryId) {
        return null;
    }

    @DeleteMapping("/{categoryId}/attributes/{attributeId}")
    public ResponseEntity<?> deleteAttribute(@PathVariable long categoryId, long attributeId) {
        return null;
    }

}
