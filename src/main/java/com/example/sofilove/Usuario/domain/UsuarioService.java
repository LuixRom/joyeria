package com.example.sofilove.usuario.domain;

import com.example.sofilove.carrito.domain.Carrito;
import com.example.sofilove.usuario.dto.UsuarioRequestDto;
import com.example.sofilove.usuario.dto.UsuarioResponseDto;
import com.example.sofilove.usuario.infrastructure.UsuarioRepository;

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
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, ApplicationEventPublisher eventPublisher) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
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

        CreateAccountEvent event = new CreateAccountEvent(this, usuario.getEmail(), usuario.getNombre());
        eventPublisher.publishEvent(event);
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
