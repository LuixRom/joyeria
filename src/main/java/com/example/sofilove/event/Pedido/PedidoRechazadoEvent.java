package com.example.sofilove.event.Pedido;

import com.example.sofilove.Pedido.domain.Pedido;

public class PedidoRechazadoEvent extends PedidoEvent {
    public PedidoRechazadoEvent(Object source, Pedido pedido) {
        super(source, pedido);
    }
}
