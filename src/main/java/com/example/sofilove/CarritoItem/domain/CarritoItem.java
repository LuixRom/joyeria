package com.example.sofilove.CarritoItem.domain;

import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Product.domain.Product;
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

    @PrePersist
    @PreUpdate
    private void calcularSubtotal() {
        if (cantidad != null && product != null && product.getPrice() != null) {
            this.subtotal = cantidad * product.getPrice();
        }
    }
}
