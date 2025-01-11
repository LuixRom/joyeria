package com.example.sofilove.Review.infrastructure;

import com.example.sofilove.Review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
