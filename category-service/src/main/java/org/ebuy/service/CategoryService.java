package org.ebuy.service;

import org.ebuy.exception.CategoryNotFoundException;
import org.ebuy.model.category.Category;
import org.ebuy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Service
@Transactional
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategory(long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        return categoryOptional.orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    public Category createCategory(Category category) {
       return categoryRepository.save(category);
    }

    public Category updateCategory(long categoryId, Category updatedCategory) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return null;
    }

    public void deleteCategory(long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.orElseThrow(() -> new CategoryNotFoundException(categoryId));
        categoryRepository.delete(category);
    }

}
