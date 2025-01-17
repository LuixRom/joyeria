package com.example.sofilove.Carrito.domain;

import com.example.sofilove.CarritoItem.domain.CarritoItem;
import com.example.sofilove.Usuario.domain.Usuario;
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

    @OneToOne
    private Usuario usuario;

    private Double total = 0.0; // por default en 0 soles


    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<CarritoItem> items;
}
