package com.example.sofilove.Review.domain;

import com.example.sofilove.Product.domain.Product;
import com.example.sofilove.Usuario.domain.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

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
