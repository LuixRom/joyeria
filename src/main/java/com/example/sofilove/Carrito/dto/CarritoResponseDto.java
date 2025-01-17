package com.example.sofilove.Carrito.dto;

import com.example.sofilove.CarritoItem.dto.CarritoItemResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarritoResponseDto {
    private Long id;
    private Double total;
    private List<CarritoItemResponseDto> items;
}
