package com.example.sofilove.Carrito.infrastructure;

import com.example.sofilove.Carrito.domain.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
