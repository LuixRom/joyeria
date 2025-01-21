package com.example.sofilove.auth.utils;

import com.example.sofilove.Usuario.domain.Role;
import com.example.sofilove.Usuario.domain.Usuario;
import com.example.sofilove.Usuario.domain.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {
    @Autowired
    private UsuarioDetailsServiceImpl userDetailsService;

    public boolean isAdminOrResourceOwner(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica si hay un usuario autenticado.
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // En este caso, el correo es el nombre de usuario.

        // Busca al usuario por su email y rol.
        Usuario usuario = userDetailsService.loadUserByUsername(email);

        // Verifica si el usuario es el propietario del recurso o es administrador.
        return usuario.getId().equals(id) || usuario.getRole().equals(Role.ADMIN);
    }

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        return true;
    }
}
