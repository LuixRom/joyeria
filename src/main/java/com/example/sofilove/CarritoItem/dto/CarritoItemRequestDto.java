package com.example.sofilove.carritoItem.dto;


import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;


@Getter
@Setter
public class CarritoItemRequestDto {
    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productId;

    @NotNull(message = "La cantidad no puede ser nula")
    @Positive(message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}
