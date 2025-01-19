package com.example.sofilove.Product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String color;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;
    @NotNull
    private Integer stock;
    @NotNull
    private Long categoryId;
    @NotNull
    private Boolean isDiscount;
    @NotNull
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer descuento;
    @NotNull
    @Size(min = 1, message = "Debe contener al menos una imagen")
    private List<String> imagenes;
}