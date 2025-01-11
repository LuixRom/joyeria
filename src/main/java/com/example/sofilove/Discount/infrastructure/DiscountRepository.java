package com.example.sofilove.Discount.infrastructure;

import com.example.sofilove.Discount.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByName(String name);
}
