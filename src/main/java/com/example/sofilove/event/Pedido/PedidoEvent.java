package com.example.sofilove.event.Pedido;

import com.example.sofilove.Pedido.domain.Pedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class PedidoEvent extends ApplicationEvent {
    private final Pedido pedido;

    public PedidoEvent(Object source, Pedido pedido) {
        super(source);
        this.pedido = pedido;
    }
}
