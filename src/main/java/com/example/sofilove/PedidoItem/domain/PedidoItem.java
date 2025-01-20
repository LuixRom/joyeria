package com.example.sofilove.PedidoItem.domain;


import com.example.sofilove.Pedido.domain.Pedido;
import com.example.sofilove.Product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private Integer cantidad = 1; // Cantidad de este producto en el carrito;

    private Double subtotal;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Product producto;

}
