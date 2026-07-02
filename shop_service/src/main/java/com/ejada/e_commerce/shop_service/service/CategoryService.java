package com.ejada.e_commerce.shop_service.service;

import com.ejada.e_commerce.shop_service.exception.CategoryAlreadyExistsException;
import com.ejada.e_commerce.shop_service.exception.CategoryNotFoundException;
import com.ejada.e_commerce.shop_service.model.Category;
import com.ejada.e_commerce.shop_service.model.Product;
import com.ejada.e_commerce.shop_service.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category doesn't exist"));
    }

    public Long createCategory(Category category) {
        categoryRepository.findByNameIgnoreCase(category.getName())
                .ifPresent(c -> {
                    throw new CategoryAlreadyExistsException("Category already exists");
                });
        return categoryRepository.save(category).getId();
    }

    public Long deleteCategory(Long categoryId) {
        Category category = this.getCategoryById(categoryId);
        categoryRepository.delete(category);
        return categoryId;
    }
}