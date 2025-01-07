package com.example.sofilove.Pedido.domain;

import com.example.sofilove.ComprobantePago.domain.ComprobantePago;
import com.example.sofilove.Direccion.domain.Direccion;
import com.example.sofilove.PedidoItem.domain.PedidoItem;
import com.example.sofilove.Usuario.domain.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoItem> items;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @Embedded
    private ComprobantePago comprobantePago;

    @Embedded
    private Direccion direccion;

    @NotNull(message = "El total no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total debe ser mayor o igual a 0")
    private Double total;

    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @PrePersist
    private void prePersist() {
        if (this.fechaPedido == null) {
            this.fechaPedido = LocalDateTime.now();
        }
    }
}
