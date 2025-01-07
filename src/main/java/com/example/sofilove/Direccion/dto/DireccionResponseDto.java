package com.example.sofilove.Direccion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DireccionResponseDto {
    private String pais = "Perú";
    private String departamento;
    private String distrito;
    private String calle;
    private String referencia;
}
