package com.example.sofilove.Carrito.domain;

import com.example.sofilove.Carrito.dto.CarritoResponseDto;
import com.example.sofilove.Carrito.infrastructure.CarritoRepository;
import com.example.sofilove.Usuario.domain.Usuario;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;
import com.example.sofilove.auth.utils.AuthorizationUtils;
import com.example.sofilove.exception.ResourceNotFound;
import com.example.sofilove.exception.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public CarritoService(AuthorizationUtils authorizationUtils ,CarritoRepository carritoRepository, ModelMapper modelMapper, UsuarioRepository usuarioRepository) {
        this.carritoRepository = carritoRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.authorizationUtils = authorizationUtils;

    }

    public CarritoResponseDto getCarritoById(Long usuarioId) {
        Carrito carrito = carritoRepository.findById(usuarioId).
                orElseThrow(()-> new ResourceNotFound("Carrito no encontrado para el usuario"));


        return modelMapper.map(carrito, CarritoResponseDto.class);
    }

    public void deleteCarritoById(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(
                () -> new ResourceNotFound("Carrito no encontrado")
        );
        carritoRepository.delete(carrito);
    }

    public void emptyCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new ResourceNotFound("Carrito no encontrado"));

        if (!authorizationUtils.isAdminOrResourceOwner(carrito.getUsuario().getId())) {
            throw new UnauthorizeOperationException("You do not have permission to empty this carrito.");
        }

        carrito.getItems().clear();
        carrito.setTotal(0.0);
        carritoRepository.save(carrito);
    }

    public CarritoResponseDto getCarritoMe(){
        // Obtener el principal del contexto de seguridad (usuario autenticado)
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();  // Obtener el email del principal
        } else {
            throw new RuntimeException("No autorizado para esta operación");
        }

        // Buscar al usuario en la base de datos utilizando el email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        return modelMapper.map(usuario.getCarrito(), CarritoResponseDto.class);

    }

}
