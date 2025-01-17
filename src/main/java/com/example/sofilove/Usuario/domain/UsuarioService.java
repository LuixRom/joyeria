package com.example.sofilove.Usuario.domain;

import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Carrito.infrastructure.CarritoRepository;
import com.example.sofilove.Usuario.dto.UsuarioRequestDto;
import com.example.sofilove.Usuario.dto.UsuarioResponseDto;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;

import com.example.sofilove.event.usuario.CreateAccountEvent;
import com.example.sofilove.exception.ResourceConflict;
import com.example.sofilove.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CarritoRepository carritoRepository;

    private final ModelMapper modelMapper;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, ApplicationEventPublisher eventPublisher, CarritoRepository carritoRepository) {
        this.eventPublisher = eventPublisher;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.carritoRepository = carritoRepository;
    }

    public UsuarioResponseDto create(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = new Usuario();
        modelMapper.map(usuarioRequestDto, usuario);

        Carrito carrito = new Carrito();


        usuario.setCarrito(carrito);
        usuario.setRole(Role.CLIENTE);


        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new ResourceConflict("El email ya existe");
        }
        usuarioRepository.save(usuario);

        carrito.setUsuario(usuario);
        carritoRepository.save(carrito);

        eventPublisher.publishEvent(new CreateAccountEvent(this, usuario.getEmail(), usuario.getNombre()));
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public UsuarioResponseDto getUserById(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFound("usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioResponseDto.class);

    }

    public void delete(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFound("usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    public UsuarioResponseDto update(Long id, UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFound("usuario no encontrado"));
        modelMapper.map(usuarioRequestDto, usuario);
        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioResponseDto.class);
    }

    public List<UsuarioResponseDto> getAllUsers(){
        List<Usuario> users = usuarioRepository.findAll();

        return users.stream().map(user -> modelMapper.map(user, UsuarioResponseDto.class)).toList();
    }


}
