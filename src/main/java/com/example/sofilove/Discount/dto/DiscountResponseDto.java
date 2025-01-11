package com.example.sofilove.Discount.dto;

import com.example.sofilove.Category.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DiscountResponseDto {
    private String name; // Nombre del descuento
    private String description; // Descripción
    private Double porcentaje; // Porcentaje aplicado
    private LocalDateTime fechaInicio; // Fecha de inicio del descuento
    private LocalDateTime fechaFin; // Fecha de fin del descuento
    private Boolean activo; // Estado del descuento
    private List<String> productNames; // Lista de nombres de las categorías asociadas
}
