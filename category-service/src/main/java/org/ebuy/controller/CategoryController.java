package org.ebuy.controller;

import org.ebuy.exception.CategoryNotFoundException;
import org.ebuy.model.category.Category;
import org.ebuy.model.category.request.CategoryRequest;
import org.ebuy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    private CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable long categoryId) {
        return ResponseEntity.ok(categoryService.findCategory(categoryId));
    }

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createCategory(null));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable long categoryId, CategoryRequest request) {
        Category updatedCategory = categoryService.updateCategory(categoryId, null);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return (ResponseEntity<?>) ResponseEntity.ok();
    }

}
