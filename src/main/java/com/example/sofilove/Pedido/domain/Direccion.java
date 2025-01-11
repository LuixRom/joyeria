package com.example.sofilove.Pedido.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Direccion {
    @NotBlank(message = "El país no puede estar vacío")
    private String pais;

    @NotBlank(message = "El departamento no puede estar vacío")
    private String departamento;

    @NotBlank(message = "El distrito no puede estar vacío")
    private String distrito;

    @NotBlank(message = "La calle no puede estar vacía")
    private String calle;

    @NotBlank(message = "La calle no puede estar vacía")
    private String referencia;

    @PrePersist
    private void setDefaultPais() {
        if (this.pais == null) {
            this.pais = "Perú";
        }
    }
}



