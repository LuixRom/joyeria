package com.example.sofilove.Review.infrastructure;

import com.example.sofilove.Review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUsuario_Id(Long user_id);

    List<Review> findByProduct_Id(Long productId);
}
