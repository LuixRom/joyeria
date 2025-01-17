package com.example.sofilove.Category.dto;


import com.example.sofilove.Product.domain.Product;
import com.example.sofilove.Product.dto.ProductResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponseDto {
    private String name;
    private String description;
}
