package com.example.sofilove.Review.domain;

import com.example.sofilove.Product.infrastructure.ProductRepository;
import com.example.sofilove.Review.dto.ReviewRequestDto;
import com.example.sofilove.Review.dto.ReviewResponseDto;
import com.example.sofilove.Review.infrastructure.ReviewRepository;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;
import com.example.sofilove.exception.ResourceNotFound;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper, UsuarioRepository usuarioRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.productRepository = productRepository;
    }

    public ReviewResponseDto saveReview(ReviewRequestDto reviewRequestDto){
        Review review = new Review();
        modelMapper.map(reviewRequestDto, review);

        reviewRepository.save(review);
        return modelMapper.map(review, ReviewResponseDto.class);
    }

    public void deleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFound("review not found"));
        reviewRepository.delete(review);
    }

    public ReviewResponseDto getReviewById(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFound("review not found"));

        return modelMapper.map(review, ReviewResponseDto.class);
    }

    public ReviewResponseDto updateReview(Long id, ReviewRequestDto reviewRequestDto){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFound("review not found"));
        modelMapper.map(reviewRequestDto, review);

        reviewRepository.save(review);

        return modelMapper.map(review, ReviewResponseDto.class);
    }

    public List<ReviewResponseDto> getReviewsByUser(Long user_id){
        if(usuarioRepository.existsById(user_id)){
            throw new ResourceNotFound("user not found");
        }
        List<Review> reviews = reviewRepository.findByReviewsByUser(user_id);

        return reviews.stream().map(review -> modelMapper.map(review, ReviewResponseDto.class)).toList();

    }

    public List<ReviewResponseDto> getReviewsByProduct(Long product_id){
        if(productRepository.existsById(product_id)){
            throw new ResourceNotFound("product not found");
        }
        List<Review> reviews = reviewRepository.findByReviewsByProduct(product_id);

        return reviews.stream().map(review -> modelMapper.map(review, ReviewResponseDto.class)).toList();
    }
}
