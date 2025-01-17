package com.example.sofilove.Pedido.infrastructure;

import com.example.sofilove.Pedido.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
