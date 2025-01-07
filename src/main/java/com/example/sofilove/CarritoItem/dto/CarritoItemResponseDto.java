package com.example.sofilove.CarritoItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarritoItemResponseDto {
    private Long productId;
    private String productName;
    private Double precioUnitario;
    private Integer cantidad;
    private Double subtotal;
}
