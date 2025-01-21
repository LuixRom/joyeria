package com.example.sofilove.Carrito.application;

import com.example.sofilove.Carrito.domain.CarritoService;
import com.example.sofilove.Carrito.dto.CarritoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    private final CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarritoResponseDto> getCarritoByUsuario(@PathVariable Long usuarioId) {
        CarritoResponseDto responseDto = carritoService.getCarritoById(usuarioId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{carritoId}/empty")
    public ResponseEntity<Void> emptyCarrito(@PathVariable Long carritoId) {
        carritoService.emptyCarrito(carritoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{carritoId}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long carritoId) {
        carritoService.deleteCarritoById(carritoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CarritoResponseDto> getMe() {
        CarritoResponseDto carrito =  carritoService.getCarritoMe();
        return ResponseEntity.ok(carrito);
    }

}
