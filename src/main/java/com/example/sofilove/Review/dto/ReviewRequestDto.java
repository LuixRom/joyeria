package com.example.sofilove.Review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private Long productId;
    private Long usuarioId;
    private String comentario;
    private Integer calificacion;
}
