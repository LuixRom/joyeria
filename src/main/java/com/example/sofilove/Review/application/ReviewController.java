package com.example.sofilove.Review.application;

import com.example.sofilove.Review.domain.ReviewService;
import com.example.sofilove.Review.dto.ReviewRequestDto;
import com.example.sofilove.Review.dto.ReviewResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/review"))
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.saveReview(reviewRequestDto);
        return ResponseEntity.ok(reviewResponseDto);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId){
        ReviewResponseDto reviewResponseDto = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(reviewResponseDto);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.updateReview(reviewId, reviewRequestDto);
        return ResponseEntity.ok(reviewResponseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByUser(@PathVariable Long userId){
        List<ReviewResponseDto> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByProduct(@PathVariable Long productId){
        List<ReviewResponseDto> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }
}

