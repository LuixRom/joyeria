package com.example.sofilove.pedido.domain;

import com.example.sofilove.carrito.domain.Carrito;
import com.example.sofilove.carrito.domain.CarritoService;
import com.example.sofilove.carrito.infrastructure.CarritoRepository;
import com.example.sofilove.pedido.dto.PedidoRequestDto;
import com.example.sofilove.pedido.dto.PedidoResponseDto;
import com.example.sofilove.pedido.infrastructure.PedidoRepository;
import com.example.sofilove.pedidoItem.domain.PedidoItem;
import com.example.sofilove.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;
    private final CarritoRepository carritoRepository;
    private final CarritoService carritoService;
    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ModelMapper modelMapper, CarritoRepository carritoRepository, CarritoService carritoService) {
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
        this.carritoRepository = carritoRepository;
        this.carritoService = carritoService;
    }
    public PedidoResponseDto crearPedido(PedidoRequestDto pedidoRequestDto) {
        Carrito carrito = carritoRepository.findById(pedidoRequestDto.getCarritoId()).orElseThrow(() -> new ResourceNotFound("carrito not found"));
        Pedido pedido = modelMapper.map(pedidoRequestDto, Pedido.class);
        pedido.setTotal(carrito.getTotal());
        List<PedidoItem> pedidoItems = carrito.getItems().stream().map(carritoItem -> modelMapper.map(carritoItem, PedidoItem.class)).toList();
        pedido.setTotal(carrito.getTotal());
        pedido.setItems(pedidoItems);
        pedido.setUsuario(carrito.getUsuario());
        pedidoRepository.save(pedido);
        carritoService.emptyCarrito(pedido.getId());
        return modelMapper.map(pedido, PedidoResponseDto.class);
    }
    public List<PedidoResponseDto> getallPedidos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoResponseDto.class)).toList();
    }
    public PedidoResponseDto getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(()-> new ResourceNotFound("pedido not found"));
        return modelMapper.map(pedido, PedidoResponseDto.class);
    }
    public void deletePedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Pedido not found"));
        pedidoRepository.delete(pedido);
    }
}