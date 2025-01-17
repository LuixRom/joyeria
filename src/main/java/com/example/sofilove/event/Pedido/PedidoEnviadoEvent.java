package com.example.sofilove.event.Pedido;


import com.example.sofilove.Pedido.domain.Pedido;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PedidoEnviadoEvent extends ApplicationEvent {

    private final Pedido pedido;

    public PedidoEnviadoEvent(Object source, Pedido pedido) {
        super(source);
        this.pedido = pedido;
    }
    public Pedido getPedido() {
        return pedido;
    }
}