package com.example.sofilove.PedidoItem.domain;

import com.example.sofilove.Pedido.domain.Pedido;
import com.example.sofilove.Product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Product product;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor a 0")
    private Double precioUnitario; // Precio del producto en este pedido

    private Double subtotal; // Calculado como cantidad * precioUnitario

    @PrePersist
    @PreUpdate
    private void calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = cantidad * precioUnitario;
        }
    }

}
