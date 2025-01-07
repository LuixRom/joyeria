package com.example.sofilove.ComprobantePago.dto;

import com.example.sofilove.ComprobantePago.domain.TipoDocumento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComprobantePagoRequestDto {
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
}
