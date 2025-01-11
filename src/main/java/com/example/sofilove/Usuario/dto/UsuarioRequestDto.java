package com.example.sofilove.Usuario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDto {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String phone;
    private String role;
}
