package com.example.sofilove.Pedido.domain;


import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.CarritoItem.domain.CarritoItem;
import com.example.sofilove.PedidoItem.domain.PedidoItem;
import com.example.sofilove.Usuario.domain.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @OneToMany
    private List<PedidoItem> items;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max=50,message= "El nombre no puede tener más de 50 caracteres")
    private String nombreFacturacion;

    @NotBlank(message= "El apellido no puede estar vacío")
    @Size(max= 50,message="El appelido no puede tener más de 50 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Size(max=50, message = "El email no puede tener más de 50 caracteres")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El número de celular no puede estar vacío")
    @Pattern(regexp = "^9[0-9]{8}$", message = "El número de celular debe empezar por 9 y tener 9 dígitos")
    private String phone;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max=50,message= "El nombre no puede tener más de 50 caracteres")
    private String nombreComprobante;

    @NotBlank(message = "El número de celular no puede estar vacío")
    private String numero;

    @NotBlank(message = "El número de celular no puede estar vacío")
    private String DomicilioFiscal;


    @NotBlank(message = "El país no puede estar vacío")
    private String pais;

    @NotBlank(message = "El departamento no puede estar vacío")
    private String departamento;

    @NotBlank(message = "El distrito no puede estar vacío")
    private String distrito;

    @NotBlank(message = "La calle no puede estar vacía")
    private String calle;

    @NotBlank(message = "La calle no puede estar vacía")
    private String referencia;

    @NotNull(message = "El total no puede estar vacío")
    @DecimalMin(value = "0.0", inclusive = true, message = "El total debe ser mayor o igual a 0")
    private Double total;

    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Documento documento;

    @PrePersist
    private void prePersist() {
        if (this.fechaPedido == null) {
            this.fechaPedido = LocalDateTime.now();
        }

        if (this.pais == null) {
            this.pais = "Perú";
        }

        if(this.estado == null) {
            this.estado = Estado.PENDIENTE;
        }
    }

}
