package com.example.sofilove.CarritoItem.application;

import com.example.sofilove.CarritoItem.domain.CarritoItemService;
import com.example.sofilove.CarritoItem.dto.CarritoItemRequestDto;
import com.example.sofilove.CarritoItem.dto.CarritoItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
public class CarritoItemController {
    private final CarritoItemService carritoItemService;

    @Autowired
    public CarritoItemController(CarritoItemService carritoItemService) {
        this.carritoItemService = carritoItemService;
    }

    @PostMapping("/{carritoId}/add")
    public ResponseEntity<CarritoItemResponseDto> addItemToCart(@PathVariable Long carritoId, @RequestBody CarritoItemRequestDto requestDto) {
        CarritoItemResponseDto responseDto = carritoItemService.addItemCarrito(carritoId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{itemId}/update")
    public ResponseEntity<CarritoItemResponseDto> updateItemQuantity(@PathVariable Long itemId, @RequestBody CarritoItemRequestDto requestDto) {
        CarritoItemResponseDto responseDto = carritoItemService.updateItem(itemId, requestDto.getCantidad());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemFromCart(@PathVariable Long itemId) {
        carritoItemService.deleteItemCarrito(itemId);
        return ResponseEntity.noContent().build();
    }
}
