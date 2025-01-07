package com.example.sofilove.Product.domain;

import com.example.sofilove.Category.domain.Category;
import com.example.sofilove.Discount.domain.Discount;
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
    private String name;

    @NotBlank(message= "La descripcion no debe estar vacío")
    @Size(max=500, message = "La descripcion debe de tener como máximo 500 caracteres")
    private String description;

    @NotBlank(message = "El color no debe de estar vacio")
    @Size(max=20, message = "El color debe de tener como máximo 20 caracteres")
    private String color;

    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "El stock no puede estar vacía")
    @Min(value = 0, message = "El stock no puede ser negativa")
    private Integer stock;

    @NotBlank(message = "El nombre del archivo no puede estar vacía")
    @ElementCollection
    private List<String> imagenes; // Nombre o clave del archivo en S3

    @NotBlank(message = "Lista de beneficios no puede estar vacía")
    @ElementCollection
    private List<String> beneficios;

    @NotBlank(message = "lista de contenido no puede estar vacía")
    @ElementCollection
    private List<String> contenidos;

    @NotBlank(message = "lista de caracteristicas no puede estar vacía")
    @ElementCollection
    private List<String> caracteristicas;

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "products")
    private List<Discount> discounts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", updatable = false)
    private LocalDateTime fechaActualizacion;
}
