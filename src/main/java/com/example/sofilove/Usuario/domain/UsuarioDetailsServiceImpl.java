package com.example.sofilove.Usuario.domain;

import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException{

        return usuarioRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User no encontrado con el email " + email));
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email ->{
            Usuario user =usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User no encontrado con el email " + email));
            return(UserDetails) user;
        };
    }
}
