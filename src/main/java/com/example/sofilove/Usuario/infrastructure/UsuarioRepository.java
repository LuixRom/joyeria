package com.example.sofilove.usuario.infrastructure;

import com.example.sofilove.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Boolean existsByEmail(String email);
}
