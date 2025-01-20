package com.example.sofilove.CarritoItem.domain;

import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Carrito.infrastructure.CarritoRepository;
import com.example.sofilove.CarritoItem.dto.CarritoItemRequestDto;
import com.example.sofilove.CarritoItem.dto.CarritoItemResponseDto;
import com.example.sofilove.CarritoItem.infrastructure.CarritoItemRepository;
import com.example.sofilove.Product.domain.Product;
import com.example.sofilove.Product.infrastructure.ProductRepository;
import com.example.sofilove.exception.ResourceConflict;
import com.example.sofilove.exception.ResourceNotFound;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CarritoItemService {
    private final CarritoItemRepository carritoItemRepository;
    private final CarritoRepository carritoRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarritoItemService(CarritoItemRepository carritoItemRepository, CarritoRepository carritoRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.carritoItemRepository = carritoItemRepository;
        this.carritoRepository = carritoRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public CarritoItemResponseDto addItemCarrito(Long carritoId, CarritoItemRequestDto requestDto) {
        Carrito carrito = carritoRepository.findById(carritoId).
                orElseThrow(()-> new ResourceNotFound("El carrito no existe"));

        Product product = productRepository.findById(requestDto.getProductId()).
                orElseThrow(()-> new ResourceNotFound("El producto no existe"));

        if(carritoItemRepository.findByProduct_Id(product.getId()) != null){
             throw new ResourceConflict("ya existe el producto");
        }

        CarritoItem carritoItem = new CarritoItem();
        carritoItem.setProduct(product);
        carritoItem.setCantidad(requestDto.getCantidad());
        carritoItem.setSubtotal(product.getPrice() * requestDto.getCantidad());
        carrito.setTotal(carrito.getTotal() + carritoItem.getSubtotal());
        carritoItem.setCarrito(carrito);

        return modelMapper.map(carritoItemRepository.save(carritoItem), CarritoItemResponseDto.class);
    }

    public CarritoItemResponseDto updateItem(Long itemId, Integer nuevaCantidad) {
        CarritoItem carritoItem= carritoItemRepository.findById(itemId).
                orElseThrow(()-> new ResourceNotFound("El item no existe"));

        Carrito carrito = carritoRepository.findById(carritoItem.getCarrito().getId()).orElseThrow(()-> new ResourceNotFound("El carrito no existe"));
        carritoItem.setCantidad(nuevaCantidad);
        carritoItem.setSubtotal(carritoItem.getProduct().getPrice() * nuevaCantidad);

        Double total = carrito.getItems().stream().mapToDouble(CarritoItem::getSubtotal).sum();
        carrito.setTotal(total);

        carritoItemRepository.save(carritoItem);
        return modelMapper.map(carritoItem, CarritoItemResponseDto.class);
    }

    public void deleteItemCarrito(Long itemId) {
        CarritoItem carritoItem = carritoItemRepository.findById(itemId).
                orElseThrow(()-> new ResourceNotFound("El item no existe"));
        carritoItemRepository.deleteById(carritoItem.getId());
    }

}
