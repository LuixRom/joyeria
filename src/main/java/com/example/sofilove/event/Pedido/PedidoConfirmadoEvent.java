package com.example.sofilove.event.Pedido;


import com.example.sofilove.Pedido.domain.Pedido;

public class PedidoConfirmadoEvent extends PedidoEvent {
    public PedidoConfirmadoEvent(Object source, Pedido pedido) {
        super(source, pedido);
    }
}