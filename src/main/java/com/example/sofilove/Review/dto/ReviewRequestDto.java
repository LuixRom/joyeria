package com.example.sofilove.review.dto;

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
