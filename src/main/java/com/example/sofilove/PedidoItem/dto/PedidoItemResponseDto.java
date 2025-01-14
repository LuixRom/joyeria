package com.example.sofilove.PedidoItem.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoItemResponseDto {
    private Long productId;
    private String productName;
    private Integer cantidad;
    private Double subtotal;
}
