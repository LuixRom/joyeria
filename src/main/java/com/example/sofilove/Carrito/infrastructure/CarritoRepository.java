package com.example.sofilove.carrito.infrastructure;

import com.example.sofilove.carrito.domain.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
