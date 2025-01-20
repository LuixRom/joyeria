package com.example.sofilove.Product.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequestDto {
    @NotBlank(message= "El nombre no debe estar vacío")
    @Size(max=50, message= "El nombre debe de tener como maximo 50 caracteres")
    @Column(unique = true)
    private String name;

    @NotBlank(message= "La descripcion no debe estar vacío")
    @Size(max=500, message = "La descripcion debe de tener como máximo 500 caracteres")
    private String description;

    @NotBlank(message = "El color no debe de estar vacio")
    @Size(max=20, message = "El color debe de tener como máximo 20 caracteres")
    private String color;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "El stock no puede estar vacía")
    @Min(value = 0, message = "El stock no puede ser negativa")
    private Integer stock;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imagenes; // Nombre o clave del archivo en S3

    @NotNull
    private Long categoryId;

    @NotNull
    private Boolean isDiscount;

    private Integer descuento;
}