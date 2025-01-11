package com.example.sofilove.Usuario.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioResponseDto {
    private String nombre;
    private String apellido;
    private String email;
    private String phone;
    private String role;
    private LocalDateTime fechaRegistro;
}
