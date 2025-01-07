package com.example.sofilove.Carrito.domain;

import com.example.sofilove.CarritoItem.domain.CarritoItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<CarritoItem> items;
}
