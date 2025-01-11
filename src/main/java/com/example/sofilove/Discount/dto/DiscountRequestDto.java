package com.example.sofilove.Discount.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DiscountRequestDto {
    private String name; // Nombre del descuento
    private String description; // Descripción del descuento
    private Double porcentaje; // Porcentaje de descuento
    private LocalDateTime fechaInicio; // Si es opcional, puedes quitarlo
    private LocalDateTime fechaFin;
    private Boolean activo; // Estado del descuento (activo/inactivo)
    private List<Long> productIds; // Lista de IDs de categorías para asociar
}
