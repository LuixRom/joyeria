package com.example.sofilove.Category.application;

import com.example.sofilove.Category.domain.CategoryService;
import com.example.sofilove.Category.dto.CategoryRequestDto;
import com.example.sofilove.Category.dto.CategoryResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.saveCategory(categoryRequestDto);
        return ResponseEntity.created(null).body(categoryResponseDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryResponseDtoList);
    }


    @GetMapping("/{categoryId}")
    private ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryResponseDto);
    }


    @PutMapping("/{categoryId}")
    private ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId,@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId, categoryRequestDto);
        return ResponseEntity.ok(categoryResponseDto);
    }

}
