package com.example.sofilove.Category.domain;

import com.example.sofilove.Discount.domain.Discount;
import com.example.sofilove.Product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    @ManyToMany(mappedBy = "categories")
    private List<Discount> discounts;
}
