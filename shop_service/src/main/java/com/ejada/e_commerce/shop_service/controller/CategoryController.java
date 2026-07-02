package com.ejada.e_commerce.shop_service.controller;

import com.ejada.e_commerce.shop_service.model.Category;
import com.ejada.e_commerce.shop_service.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Category>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryById(categoryId));
    }

    @PostMapping("/add-category")
    public ResponseEntity<String> createCategory(Category category) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(String.format("Created category with id: %d", categoryService.createCategory(category)));
    }

    @DeleteMapping("/{categoryId}/delete-category")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Deleted category with id: %d", categoryService.deleteCategory(categoryId)));
    }
}
