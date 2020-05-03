package org.ebuy.service;

import org.ebuy.exception.*;
import org.ebuy.helper.CategoryUtil;
import org.ebuy.model.Attribute;
import org.ebuy.model.Category;
import org.ebuy.model.CategoryAttribute;
import org.ebuy.repository.AttributeRepository;
import org.ebuy.repository.CategoryAttributeRepository;
import org.ebuy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Service
@Transactional
public class CategoryService {

    private CategoryRepository categoryRepository;
    private AttributeRepository attributeRepository;
    private CategoryAttributeRepository categoryAttributeRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           AttributeRepository attributeRepository,
                           CategoryAttributeRepository categoryAttributeRepository) {
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    public Category findCategory(long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findCategoryById(categoryId);
        return categoryOptional.orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    public Category createCategory(Category category, long parentCategoryId) {
        Category parentCategory = null;
        if(parentCategoryId != 0) {
            Optional<Category> parentCategoryOptional = categoryRepository.findById(parentCategoryId);
            parentCategory = parentCategoryOptional.orElseThrow(() -> new ParentCategoryNotFoundException(parentCategoryId));
        }

        String beautifiedName = CategoryUtil.beautifyCategoryName(category.getName());
        Optional<Category> categoryOptional = categoryRepository.findByBeautifiedName(beautifiedName);
        if(categoryOptional.isPresent()) {
            throw new BeautifiedNameException(beautifiedName);
        }
        category.setBeautifiedName(beautifiedName);

        if(parentCategory != null) {
            parentCategory.addSubCategory(category);
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(long categoryId, Category updatedCategory, long parentCategoryId) {
        Category parentCategory = null;
        if(parentCategoryId != 0) {
            Optional<Category> parentCategoryOptional = categoryRepository.findById(parentCategoryId);
            parentCategory = parentCategoryOptional.orElseThrow(() -> new ParentCategoryNotFoundException(parentCategoryId));
        }

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.orElseThrow(() -> new CategoryNotFoundException(categoryId));

        if(parentCategoryId != 0) {
            if(category.getParent() != null && category.getParent().getId() != parentCategoryId) {
                if(CollectionUtils.isEmpty(category.getSubCategories())) {
                    category.getParent().removeSubCategory(category);
                    parentCategory.addSubCategory(category);
                } else {
                    throw new CategoryUpdateException("Category cannot be updated due to having sub categories");
                }
            } else if(category.getParent() == null){
                parentCategory.addSubCategory(category);
            }
        }
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.orElseThrow(() -> new CategoryNotFoundException(categoryId));
        if(category.getParent() != null) {
            category.getParent().removeSubCategory(category);
        }
        categoryRepository.delete(category);
    }

    public CategoryAttribute addAttributeToCategory(long categoryId, CategoryAttribute categoryAttribute, long attributeId) {
        /* category */
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category category = categoryOptional.orElseThrow(() -> new CategoryNotFoundException(categoryId));
        /* attribute */
        Optional<Attribute> attributeOptional = attributeRepository.findById(attributeId);
        Attribute attribute = attributeOptional.orElseThrow(() -> new AttributeNotFoundException(categoryId));

        categoryAttribute.setAttribute(attribute);
        category.addCategoryAttribute(categoryAttribute);
        categoryRepository.save(category);
        return categoryAttribute;
    }

}
