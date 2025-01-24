package com.example.sofilove.PedidoItem.infrastructure;

import com.example.sofilove.Pedido.domain.Pedido;
import com.example.sofilove.PedidoItem.domain.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByPedido_Id(Long pedidoId);
}
