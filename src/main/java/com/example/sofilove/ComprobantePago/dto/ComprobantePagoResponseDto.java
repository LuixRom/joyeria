package com.example.sofilove.ComprobantePago.dto;

import com.example.sofilove.ComprobantePago.domain.TipoDocumento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComprobantePagoResponseDto {
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String nombre;
}
