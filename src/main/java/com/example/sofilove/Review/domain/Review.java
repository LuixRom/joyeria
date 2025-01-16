package com.example.sofilove.review.domain;

import com.example.sofilove.product.domain.Product;
import com.example.sofilove.usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;


@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;

    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 5, message = "La calificación debe ser como máximo 5")
    private Integer calificacion;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Usuario usuario;
}
