package com.example.sofilove.Usuario.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Size(max = 50, message = "El email no puede tener más de 50 caracteres")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 20, message = "La contraseña debe tener como mínimo 8 caracteres")
    private String password;

    @NotNull
    @NotBlank(message = "El número de celular no puede estar vacío")
    @Pattern(regexp = "^9[0-9]{8}$", message = "El número de celular debe empezar por 9 y tener 9 dígitos")
    private String phone;
}
