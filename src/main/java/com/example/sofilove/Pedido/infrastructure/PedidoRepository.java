package com.example.sofilove.Pedido.infrastructure;

import com.example.sofilove.Pedido.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByDireccion_Departamento(String departamento);

    List<Pedido> findByDireccion_Distrito(String distrito);
}
