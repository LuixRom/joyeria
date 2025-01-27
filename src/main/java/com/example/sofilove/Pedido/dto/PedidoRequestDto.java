package com.example.sofilove.Pedido.dto;


import com.example.sofilove.CarritoItem.dto.CarritoItemRequestDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoRequestDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max=50,message= "El nombre no puede tener más de 50 caracteres")
    private String nombreFacturacion;

    @NotBlank(message= "El apellido no puede estar vacío")
    @Size(max= 50,message="El appelido no puede tener más de 50 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Size(max=50, message = "El email no puede tener más de 50 caracteres")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El número de celular no puede estar vacío")
    @Pattern(regexp = "^9[0-9]{8}$", message = "El número de celular debe empezar por 9 y tener 9 dígitos")
    private String phone;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max=50,message= "El nombre no puede tener más de 50 caracteres")
    private String nombreComprobante;

    @NotBlank(message = "El número de celular no puede estar vacío")
    private String numero;

    @NotBlank(message = "El domicilio no puede estar vacío")
    private String domicilioFiscal;

    @NotNull
    private String documento;

    @NotNull
    private String departamento;

    @NotNull
    private String distrito;

    @NotNull
    private String calle;

    @NotNull
    private String referencia;
}
