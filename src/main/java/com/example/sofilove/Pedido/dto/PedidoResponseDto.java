package com.example.sofilove.pedido.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private String estado;
    private Double total;
    private String Documento;
    private LocalDateTime fechaPedido;
}
