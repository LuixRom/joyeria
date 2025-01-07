package com.example.sofilove.Pedido.dto;

import com.example.sofilove.PedidoItem.dto.PedidoItemResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDto {
    private Long id;
    private String usuarioNombre;
    private List<PedidoItemResponseDto> items;
    private String metodoPago;
    private String estado;
    private Double total;
    private LocalDateTime fechaPedido;
    private String direccionCompleta;
}
