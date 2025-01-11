package com.example.sofilove.Category.domain;

import com.example.sofilove.Product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
