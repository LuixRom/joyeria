package com.example.sofilove.Pedido.dto;


import com.example.sofilove.PedidoItem.dto.PedidoItemRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDto {
    private Long usuarioId;
    private List<PedidoItemRequestDto> items;

    private String departamento;
    private String distrito;
    private String calle;
    private String referencia;
}
