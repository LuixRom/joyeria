package com.example.sofilove.event.Pedido;

import com.example.sofilove.Pedido.domain.Pedido;

public class PedidoCreatedEvent extends PedidoEvent {
    public PedidoCreatedEvent(Object source, Pedido pedido) {
        super(source, pedido);
    }
}