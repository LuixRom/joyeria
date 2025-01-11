package com.example.sofilove.Review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private String nombreUsuario;
    private String nombreProducto;
    private String comentario;
    private Integer calificacion;
}
