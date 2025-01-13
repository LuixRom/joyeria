package com.example.sofilove.Product.domain;


import com.example.sofilove.Category.infrastructure.CategoryRepository;
import com.example.sofilove.Product.dto.ProductRequestDto;
import com.example.sofilove.Product.dto.ProductResponseDto;
import com.example.sofilove.Product.infrastructure.ProductRepository;
import com.example.sofilove.exception.ResourceConflict;
import com.example.sofilove.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = new Product();
        modelMapper.map(productRequestDto, product);
        if(productRepository.existsByName(product.getName())){
            throw new ResourceConflict("Product already exists");
        }
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceConflict("Product not found"));
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product not found"));
        productRepository.delete(product);
    }

    public ProductResponseDto update(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Product not found"));
        modelMapper.map(productRequestDto, product);
        productRepository.save(product);
        return modelMapper.map(product, ProductResponseDto.class);
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelMapper.map(product, ProductResponseDto.class)).toList();
    }

    public List<ProductResponseDto> getProductsByCategory(Long category_id) {
        if(categoryRepository.existsById(category_id)){
            throw new ResourceNotFound("Category not found");
        }
        List<Product> products = productRepository.findByCategoryId(category_id);
        return products.stream().map(product -> modelMapper.map(product, ProductResponseDto.class)).toList();
    }
}
