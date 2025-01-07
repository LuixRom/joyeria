package com.example.sofilove.Category.dto;

import com.example.sofilove.Discount.domain.Discount;
import com.example.sofilove.Product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponseDto {
    private String name;
    private String description;
    private List<Product> products;
    private List<Discount> discounts;
}
