package com.example.sofilove.config;

import com.example.sofilove.Usuario.domain.Role;
import com.example.sofilove.Usuario.domain.Usuario;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByEmail("luis.romero@utec.edu.pe")) {
            Usuario admin = new Usuario();
            admin.setNombre("Luis");
            admin.setApellido("Romero");
            admin.setEmail("luis.romero@utec.edu.pe");

            admin.setPassword(passwordEncoder.encode("admin123"));

            admin.setPhone("966462221");
            admin.setRole(Role.ADMIN);

            usuarioRepository.save(admin);
            System.out.println("Administrador creado con éxito.");
        } else {
            System.out.println("El administrador ya existe.");
        }
    }
}
