package com.example.sofilove.carritoItem.infrastructure;

import com.example.sofilove.carritoItem.domain.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
}
