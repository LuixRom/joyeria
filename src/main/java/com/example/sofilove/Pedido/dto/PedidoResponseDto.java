package com.example.sofilove.Pedido.dto;

import com.example.sofilove.Pedido.domain.Estado;
import com.example.sofilove.PedidoItem.dto.PedidoItemResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDto {
    private Long id;
    private String nombreFacturacion;
    private String apellido;
    private String phone;
    private String nombreComprobante;
    private String numero;
    private String departamento;
    private String distrito;
    private String calle;
    private String email;
    private Estado estado;
    private Double total;
    private String Documento;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPedido;
    private List<PedidoItemResponseDto> pedidoItems;

}
