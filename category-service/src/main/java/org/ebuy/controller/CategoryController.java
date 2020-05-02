package org.ebuy.controller;

import org.ebuy.model.Category;
import org.ebuy.model.mapper.CategoryMapper;
import org.ebuy.model.request.CategoryRequest;
import org.ebuy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/")
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
    public ResponseEntity<?> updateCategory(@PathVariable long categoryId, CategoryRequest request) {
        Category updatedCategory = categoryMapper.toCategory(request);
        Category result = categoryService.updateCategory(categoryId, updatedCategory, request.getParentCategory());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

}
