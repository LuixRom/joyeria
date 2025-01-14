package com.example.sofilove.Category.domain;

import com.example.sofilove.Category.dto.CategoryRequestDto;
import com.example.sofilove.Category.dto.CategoryResponseDto;
import com.example.sofilove.Category.infrastructure.CategoryRepository;
import com.example.sofilove.exception.ResourceConflict;
import com.example.sofilove.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryResponseDto saveCategory(CategoryRequestDto categoryRequestDto){
        Category category = new Category();

        modelMapper.map(categoryRequestDto, category);

        if(categoryRepository.existsByName(category.getName())) {
            throw new ResourceConflict("Category already exists");
        }

        categoryRepository.save(category);

        return modelMapper.map(category, CategoryResponseDto.class);
    }

    public CategoryResponseDto getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Category not found"));

        return modelMapper.map(category, CategoryResponseDto.class);
    }

    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Category not found"));

        categoryRepository.delete(category);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Category not found"));

        modelMapper.map(categoryRequestDto, category);

        categoryRepository.save(category);

        return modelMapper.map(category, CategoryResponseDto.class);
    }

}
