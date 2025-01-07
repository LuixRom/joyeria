package com.example.sofilove.Discount.domain;

import com.example.sofilove.Category.domain.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no deber estar vacio")
    @Size(max=50, message = "El nombre no puede tener mas de 50 caracteres")
    private String name;

    @NotBlank(message = "La descripcion no deber estar vacio")
    @Size(max=500, message = "La descripcion no puede tener mas de 50 caracteres")
    private String description;

    @NotNull(message = "No puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = true, message = "El porcentaje debe ser mayor o igual a 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "El porcentaje no puede ser mayor a 100")
    private Double porcentaje;

    @NotNull(message = "La fecha de inicio no puede estar vacía")
    @CreationTimestamp
    @Column(name = "fecha_inicio", updatable = false)
    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private Boolean activo;

    @ManyToMany
    private List<Category> categories;

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (fechaFin != null && fechaInicio != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }
}
