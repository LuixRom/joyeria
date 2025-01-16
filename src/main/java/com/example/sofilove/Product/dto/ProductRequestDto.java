package com.example.sofilove.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDto {
    private String name;
    private String description;
    private String color;
    private Double price;
    private Integer stock;
    private Long categoryId;
    private Boolean isDiscount;
    private Integer descuento;
    private List<String> imagenes;
}