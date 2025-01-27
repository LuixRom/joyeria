package com.example.sofilove.Review.domain;

import com.example.sofilove.Product.domain.Product;
import com.example.sofilove.Product.infrastructure.ProductRepository;
import com.example.sofilove.Review.dto.ReviewRequestDto;
import com.example.sofilove.Review.dto.ReviewResponseDto;
import com.example.sofilove.Review.infrastructure.ReviewRepository;
import com.example.sofilove.Usuario.domain.Usuario;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;
import com.example.sofilove.exception.ResourceNotFound;
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

        Usuario user = usuarioRepository.findById(reviewRequestDto.getUsuarioId()).orElseThrow(() -> new ResourceNotFound("User Not Found"));
        Product product = productRepository.findById(reviewRequestDto.getProductId()).orElseThrow(() -> new ResourceNotFound("Product Not Found"));

        review.setProduct(product);
        review.setUsuario(user);

        review.setCalificacion(reviewRequestDto.getCalificacion());
        review.setComentario(reviewRequestDto.getComentario());

        reviewRepository.save(review);
        ReviewResponseDto reviewResponse = new ReviewResponseDto();
        reviewResponse.setCalificacion(review.getCalificacion());
        reviewResponse.setComentario(review.getComentario());
        reviewResponse.setNombreProducto(review.getProduct().getName());
        reviewResponse.setNombreUsuario(review.getUsuario().getNombre());
        return reviewResponse;
    }

    public void deleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFound("review not found"));
        reviewRepository.delete(review);
    }

    public ReviewResponseDto getReviewById(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFound("review not found"));

        ReviewResponseDto reviewResponseDto = modelMapper.map(review, ReviewResponseDto.class);
        reviewResponseDto.setNombreProducto(review.getProduct().getName());
        return reviewResponseDto;
    }

    public ReviewResponseDto updateReview(Long id, ReviewRequestDto reviewRequestDto){
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFound("review not found"));
        Usuario user = usuarioRepository.findById(reviewRequestDto.getUsuarioId()).orElseThrow(() -> new ResourceNotFound("User Not Found"));
        Product product = productRepository.findById(reviewRequestDto.getProductId()).orElseThrow(() -> new ResourceNotFound("Product Not Found"));

        review.setProduct(product);
        review.setUsuario(user);
        review.setCalificacion(reviewRequestDto.getCalificacion());
        review.setComentario(reviewRequestDto.getComentario());

        reviewRepository.save(review);

        ReviewResponseDto reviewResponse = new ReviewResponseDto();
        reviewResponse.setCalificacion(review.getCalificacion());
        reviewResponse.setComentario(review.getComentario());
        reviewResponse.setNombreProducto(review.getProduct().getName());
        reviewResponse.setNombreUsuario(review.getUsuario().getNombre());
        return reviewResponse;
    }

    public List<ReviewResponseDto> getReviewsByUser(Long user_id){
        if(!usuarioRepository.existsById(user_id)){
            throw new ResourceNotFound("user not found");
        }
        List<Review> reviews = reviewRepository.findByUsuario_Id(user_id);

        return reviews.stream().map(review -> {
            ReviewResponseDto reviewResponse = modelMapper.map(review, ReviewResponseDto.class);
            reviewResponse.setNombreProducto(review.getProduct().getName());

            return reviewResponse;
        }).toList();

    }

    public List<ReviewResponseDto> getReviewsByProduct(Long product_id){
        if(!productRepository.existsById(product_id)){
            throw new ResourceNotFound("product not found");
        }
        List<Review> reviews = reviewRepository.findByProduct_Id(product_id);

        return reviews.stream().map(review -> {
            ReviewResponseDto reviewResponse = modelMapper.map(review, ReviewResponseDto.class);
            reviewResponse.setNombreProducto(review.getProduct().getName());

            return reviewResponse;
        }).toList();
    }
}
