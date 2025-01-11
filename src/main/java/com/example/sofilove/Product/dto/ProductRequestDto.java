package com.example.sofilove.Product.dto;

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
    private List<String> imagenes;
    private List<String> beneficios;
    private List<String> contenidos;
    private List<String> caracteristicas;
}