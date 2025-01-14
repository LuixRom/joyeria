package com.example.sofilove.CarritoItem.infrastructure;

import com.example.sofilove.CarritoItem.domain.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
}
