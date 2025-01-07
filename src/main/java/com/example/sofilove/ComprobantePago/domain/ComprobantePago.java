package com.example.sofilove.ComprobantePago.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ComprobantePago {
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento no puede estar vacío")
    private String numeroDocumento;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;
}
