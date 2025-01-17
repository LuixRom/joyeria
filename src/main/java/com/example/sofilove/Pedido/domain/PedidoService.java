package com.example.sofilove.Pedido.domain;


import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Carrito.domain.CarritoService;
import com.example.sofilove.Carrito.infrastructure.CarritoRepository;
import com.example.sofilove.Pedido.dto.PedidoRequestDto;
import com.example.sofilove.Pedido.dto.PedidoResponseDto;
import com.example.sofilove.Pedido.infrastructure.PedidoRepository;
import com.example.sofilove.PedidoItem.domain.PedidoItem;
import com.example.sofilove.event.Pedido.PedidoCreatedEvent;
import com.example.sofilove.event.Pedido.PedidoEnviadoEvent;
import com.example.sofilove.event.Pedido.PedidoRechazadoEvent;
import com.example.sofilove.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;
    private final CarritoRepository carritoRepository;
    private final CarritoService carritoService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

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
        pedido.setEstado(Estado.PENDIENTE);

        List<PedidoItem> pedidoItems = carrito.getItems().stream().map(carritoItem -> modelMapper.map(carritoItem, PedidoItem.class)).toList();

        pedido.setTotal(carrito.getTotal());
        pedido.setItems(pedidoItems);
        pedido.setUsuario(carrito.getUsuario());
        pedidoRepository.save(pedido);

        carritoService.emptyCarrito(pedido.getId());

        eventPublisher.publishEvent(new PedidoCreatedEvent(this, pedido));

        return modelMapper.map(pedido, PedidoResponseDto.class);
    }

    public PedidoResponseDto enviarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Pedido not found"));

        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new IllegalArgumentException("El pedido debe estar en estado PENDIENTE para ser enviado.");
        }

        pedido.setEstado(Estado.ENVIADO);
        pedidoRepository.save(pedido);

        // Publicar evento de envío de pedido
        eventPublisher.publishEvent(new PedidoEnviadoEvent(this, pedido));

        return modelMapper.map(pedido, PedidoResponseDto.class);
    }

    public PedidoResponseDto marcarComoFallido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Pedido not found"));

        pedido.setEstado(Estado.FALLIDO);
        pedidoRepository.save(pedido);

        // Publicar evento de fallo de pedido
        eventPublisher.publishEvent(new PedidoRechazadoEvent(this, pedido));

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
