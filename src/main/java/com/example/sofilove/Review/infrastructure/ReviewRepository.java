package com.example.sofilove.review.infrastructure;

import com.example.sofilove.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewsByUser(Long user_id);
    List<Review> findByReviewsByProduct(Long product_id);
}
