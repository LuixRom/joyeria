package com.example.sofilove.Usuario.domain;

import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Pedido.domain.Pedido;
import com.example.sofilove.Review.domain.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Carrito carrito;


    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max=50,message= "El nombre no puede tener más de 50 caracteres")
    private String nombre;

    @NotBlank(message= "El apellido no puede estar vacío")
    @Size(max= 50,message="El appelido no puede tener más de 50 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Size(max=50, message = "El email no puede tener más de 50 caracteres")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message="La contraseña no puede estar vacía")
    private String password;

    @NotBlank(message = "El número de celular no puede estar vacío")
    @Pattern(regexp = "^9[0-9]{8}$", message = "El número de celular debe empezar por 9 y tener 9 dígitos")
    private String phone;

    @NotNull(message = "El rol no puede estar vacío")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    private void prePersist() {
        if(this.role==null){
            this.role=Role.CLIENTE;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // Ajusta según cómo manejas los roles
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
