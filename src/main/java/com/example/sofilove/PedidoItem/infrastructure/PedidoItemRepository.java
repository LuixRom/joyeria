package com.example.sofilove.pedidoItem.infrastructure;

import com.example.sofilove.pedidoItem.domain.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    PedidoItem findByPedido(Long pedidoId);
}
