package com.example.sofilove.Carrito.dto;

import com.example.sofilove.CarritoItem.dto.CarritoItemRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarritoRequestDto {
    @NotNull(message = "La lista de ítems no puede ser nula")
    private List<CarritoItemRequestDto> items;
}
