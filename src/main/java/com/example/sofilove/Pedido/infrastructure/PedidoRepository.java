package com.example.sofilove.Pedido.infrastructure;

import com.example.sofilove.Pedido.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario_Id(Long id);
}
