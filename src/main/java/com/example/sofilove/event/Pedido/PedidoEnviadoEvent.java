package com.example.sofilove.event.Pedido;


import com.example.sofilove.Pedido.domain.Pedido;

public class PedidoEnviadoEvent extends PedidoEvent {
    public PedidoEnviadoEvent(Object source, Pedido pedido) {
        super(source, pedido);
    }
}