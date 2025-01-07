package com.example.sofilove.Pedido.dto;

import com.example.sofilove.ComprobantePago.domain.ComprobantePago;
import com.example.sofilove.Direccion.dto.DireccionRequestDto;
import com.example.sofilove.PedidoItem.dto.PedidoItemRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDto {
    private Long usuarioId;
    private List<PedidoItemRequestDto> items;
    private String metodoPago;
    private ComprobantePago comprobantePago;
    private DireccionRequestDto direccion;
}
