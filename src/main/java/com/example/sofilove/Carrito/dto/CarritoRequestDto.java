package com.example.sofilove.carrito.dto;

import com.example.sofilove.carritoItem.dto.CarritoItemRequestDto;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.util.List;

@Getter
@Setter
public class CarritoRequestDto {
    @NotNull(message = "La lista de ítems no puede ser nula")
    private List<CarritoItemRequestDto> items;

}
