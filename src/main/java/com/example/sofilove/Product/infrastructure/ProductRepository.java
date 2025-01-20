package com.example.sofilove.Product.infrastructure;

import com.example.sofilove.Product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long category_id);
    Boolean existsByName(String name);

}
