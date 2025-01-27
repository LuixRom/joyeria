package com.example.sofilove.Product.domain;

import com.example.sofilove.Category.domain.Category;
import com.example.sofilove.Review.domain.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message= "El nombre no debe estar vacío")
    @Size(max=50, message= "El nombre debe de tener como maximo 50 caracteres")
    @Column(unique = true)
    private String name;

    @NotBlank(message= "La descripcion no debe estar vacío")
    @Size(max=500, message = "La descripcion debe de tener como máximo 500 caracteres")
    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "color")
    private List<String> colores;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_fabrics", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "fabric")
    private List<String> telas;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "El stock no puede estar vacía")
    @Min(value = 0, message = "El stock no puede ser negativa")
    private Integer stock;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imagenes; // Nombre o clave del archivo en S3

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private Boolean isDiscount;

    private Integer descuento;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", updatable = false)
    private LocalDateTime fechaActualizacion;
}