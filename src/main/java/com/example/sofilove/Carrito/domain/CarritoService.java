package com.example.sofilove.carrito.domain;


import com.example.sofilove.carrito.dto.CarritoResponseDto;
import com.example.sofilove.carrito.infrastructure.CarritoRepository;
import com.example.sofilove.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarritoService(CarritoRepository carritoRepository, ModelMapper modelMapper) {
        this.carritoRepository = carritoRepository;
        this.modelMapper = modelMapper;
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

        carrito.getItems().clear();
        carrito.setTotal(0.0);
        carritoRepository.save(carrito);
    }
    

}
