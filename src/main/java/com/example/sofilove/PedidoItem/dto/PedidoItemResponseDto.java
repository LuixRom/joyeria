package com.example.sofilove.PedidoItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoItemResponseDto {
    private String productName;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
