package com.example.sofilove.PedidoItem.infrastructure;

import com.example.sofilove.PedidoItem.domain.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
}
