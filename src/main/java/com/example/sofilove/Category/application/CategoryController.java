package com.example.sofilove.Category.application;

import com.example.sofilove.Category.domain.CategoryService;
import com.example.sofilove.Category.dto.CategoryRequestDto;
import com.example.sofilove.Category.dto.CategoryResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    private ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.saveCategory(categoryRequestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @GetMapping("by-name/{name}")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@PathVariable String name) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @PutMapping("/{id}")
    private ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(id, categoryRequestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

}
