package com.example.sofilove.carrito.domain;

import com.example.sofilove.carritoItem.domain.CarritoItem;
import com.example.sofilove.usuario.domain.Usuario;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
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
