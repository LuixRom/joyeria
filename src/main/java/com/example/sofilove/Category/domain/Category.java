package com.example.sofilove.category.domain;

import com.example.sofilove.product.domain.Product;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @NotNull
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

}
