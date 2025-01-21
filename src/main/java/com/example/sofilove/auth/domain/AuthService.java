package com.example.sofilove.auth.domain;

import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Carrito.infrastructure.CarritoRepository;
import com.example.sofilove.Usuario.domain.Role;
import com.example.sofilove.Usuario.domain.Usuario;
import com.example.sofilove.Usuario.dto.UsuarioRequestDto;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;
import com.example.sofilove.auth.dtos.JwtAuthResponse;
import com.example.sofilove.auth.dtos.LoginRequest;
import com.example.sofilove.config.JwtService;
import com.example.sofilove.event.usuario.CreateAccountEvent;
import com.example.sofilove.exception.ResourceConflict;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final CarritoRepository carritoRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    AuthService(CarritoRepository carritoRepository,UsuarioRepository usuarioRepository, ModelMapper modelMapper, JwtService jwtService, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.carritoRepository = carritoRepository;
        this.eventPublisher = eventPublisher;
    }

    public JwtAuthResponse signIn(LoginRequest req) {
        Optional<Usuario> user;
        user = usuarioRepository.findByEmail(req.getUsername());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public JwtAuthResponse signUp(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = modelMapper.map(usuarioRequestDto, Usuario.class);

        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new ResourceConflict("El email ya existe");
        }

        usuario.setRole(Role.CLIENTE);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carritoRepository.save(carrito);

        usuario.setCarrito(carrito);
        usuarioRepository.save(usuario);

        eventPublisher.publishEvent(new CreateAccountEvent(this, usuario.getEmail(), usuario.getNombre()));

        //Generar la respuesta con el token JWT
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(usuario));
        return response;

    }
}
