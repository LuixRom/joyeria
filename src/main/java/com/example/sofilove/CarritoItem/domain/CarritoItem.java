package com.example.sofilove.carritoItem.domain;

import com.example.sofilove.carrito.domain.Carrito;
import com.example.sofilove.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CarritoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Carrito carrito;

    @ManyToOne
    private Product product;

    private Integer cantidad = 1; // Cantidad de este producto en el carrito

    private Double subtotal;

}
