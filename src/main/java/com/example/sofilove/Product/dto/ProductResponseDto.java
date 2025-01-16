package com.example.sofilove.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponseDto {
    private String name;
    private String description;
    private String color;
    private Double price;
    private Integer stock;
    private List<String> imagenes;
    private String categoryName;
    private Integer descuento;
}

