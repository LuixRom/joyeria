package com.example.sofilove.Usuario.domain;

import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Carrito.infrastructure.CarritoRepository;
import com.example.sofilove.Usuario.dto.UsuarioRequestDto;
import com.example.sofilove.Usuario.dto.UsuarioResponseDto;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;

import com.example.sofilove.auth.utils.AuthorizationUtils;
import com.example.sofilove.event.usuario.CreateAccountEvent;
import com.example.sofilove.exception.ResourceConflict;
import com.example.sofilove.exception.ResourceNotFound;
import com.example.sofilove.exception.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CarritoRepository carritoRepository;
    private final ModelMapper modelMapper;
    private final AuthorizationUtils authorizationUtils;


    @Autowired
    public UsuarioService(AuthorizationUtils authorizationUtils,UsuarioRepository usuarioRepository, ModelMapper modelMapper, ApplicationEventPublisher eventPublisher, CarritoRepository carritoRepository) {
        this.eventPublisher = eventPublisher;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.carritoRepository = carritoRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public UsuarioResponseDto create(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = new Usuario();
        modelMapper.map(usuarioRequestDto, usuario);

        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new ResourceConflict("El email ya existe");
        }

        usuario.setRole(Role.CLIENTE);
        usuario.setPassword(usuarioRequestDto.getPassword());
        usuarioRepository.save(usuario);

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carritoRepository.save(carrito);

        usuario.setCarrito(carrito);
        usuarioRepository.save(usuario);


        eventPublisher.publishEvent(new CreateAccountEvent(this, usuario.getEmail(), usuario.getNombre()));
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public UsuarioResponseDto getUserById(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFound("usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioResponseDto.class);

    }

    public void delete(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFound("usuario no encontrado"));
        carritoRepository.delete(usuario.getCarrito());
        usuarioRepository.delete(usuario);

    }

    public UsuarioResponseDto update(Long id, UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFound("usuario no encontrado"));
        modelMapper.map(usuarioRequestDto, usuario);

        if (!authorizationUtils.isAdminOrResourceOwner(usuario.getId())) {
            throw new UnauthorizeOperationException("You do not have permission to update this user.");
        }

        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public List<UsuarioResponseDto> getAllUsers(){
        List<Usuario> users = usuarioRepository.findAll();

        return users.stream().map(user -> modelMapper.map(user, UsuarioResponseDto.class)).toList();
    }

    public UsuarioResponseDto getUsuarioOwnInfo() {
        // Obtener el principal del contexto de seguridad (usuario autenticado)
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();  // Obtener el email del principal
        } else {
            throw new UnauthorizeOperationException("No autorizado para esta operación");
        }

        // Buscar al usuario en la base de datos utilizando el email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Usuario no encontrado"));

        // Devolver la información del usuario mapeada al DTO de respuesta
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }


}
