package com.example.sofilove.pedidoItem.domain;


import com.example.sofilove.pedido.domain.Pedido;
import com.example.sofilove.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




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
