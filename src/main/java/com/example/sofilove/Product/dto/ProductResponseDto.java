package com.example.sofilove.Product.dto;

import com.example.sofilove.Category.domain.Category;
import com.example.sofilove.Discount.domain.Discount;
import com.example.sofilove.Review.domain.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
    private List<String> beneficios;
    private List<String> contenidos;
    private List<String> caracteristicas;
    private String categoryName;
    private List<String> discountNames;
    private List<String> reviewComments;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}

