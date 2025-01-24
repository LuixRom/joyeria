package com.example.sofilove.Pedido.domain;


import com.example.sofilove.Carrito.domain.Carrito;
import com.example.sofilove.Carrito.domain.CarritoService;
import com.example.sofilove.Pedido.dto.PedidoRequestDto;
import com.example.sofilove.Pedido.dto.PedidoResponseDto;
import com.example.sofilove.Pedido.infrastructure.PedidoRepository;
import com.example.sofilove.PedidoItem.domain.PedidoItem;
import com.example.sofilove.PedidoItem.dto.PedidoItemResponseDto;
import com.example.sofilove.PedidoItem.infrastructure.PedidoItemRepository;
import com.example.sofilove.Usuario.domain.Usuario;
import com.example.sofilove.Usuario.infrastructure.UsuarioRepository;
import com.example.sofilove.auth.utils.AuthorizationUtils;
import com.example.sofilove.event.Pedido.PedidoCreatedEvent;
import com.example.sofilove.event.Pedido.PedidoConfirmadoEvent;
import com.example.sofilove.event.Pedido.PedidoRechazadoEvent;
import com.example.sofilove.exception.ResourceNotFound;
import com.example.sofilove.exception.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;
    private final CarritoService carritoService;
    private final UsuarioRepository usuarioRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Autowired
    public PedidoService(AuthorizationUtils authorizationUtils,PedidoItemRepository pedidoItemRepository,UsuarioRepository usuarioRepository, PedidoRepository pedidoRepository, ModelMapper modelMapper, CarritoService carritoService) {
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
        this.carritoService = carritoService;
        this.usuarioRepository = usuarioRepository;
        this.pedidoItemRepository = pedidoItemRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public PedidoResponseDto crearPedido(Long userId, PedidoRequestDto pedidoRequestDto) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("usuario not found"));
        Carrito carrito = usuario.getCarrito();

        if (carrito.getTotal() == 0) {
            throw new RuntimeException("carrito vacio");
        }

        Pedido pedido = modelMapper.map(pedidoRequestDto, Pedido.class);
        pedido.setEstado(Estado.PENDIENTE);
        pedido.setTotal(carrito.getTotal());
        pedido.setUsuario(usuario);

        if (!authorizationUtils.isAdminOrResourceOwner(usuario.getId())) {
            throw new UnauthorizeOperationException("You do not have permission to create this pedido.");
        }
        pedidoRepository.save(pedido);

        List<PedidoItem> pedidoItems = carrito.getItems().stream().map(carritoItem -> {
            PedidoItem pedidoItem = new PedidoItem();
            pedidoItem.setCantidad(carritoItem.getCantidad());
            pedidoItem.setSubtotal(carritoItem.getSubtotal());
            pedidoItem.setProducto(carritoItem.getProduct());
            pedidoItem.setPedido(pedido);
            return pedidoItem;
        }).toList();

        pedidoItemRepository.saveAll(pedidoItems);



        // Mapeo manual de PedidoItem a PedidoItemResponseDto
        List<PedidoItemResponseDto> pedidoItemResponseDtoList = pedidoItems.stream().map(item -> {
            PedidoItemResponseDto dto = new PedidoItemResponseDto();
            dto.setProductId(item.getProducto().getId());
            dto.setProductName(item.getProducto().getName());
            dto.setCantidad(item.getCantidad());
            dto.setSubtotal(item.getSubtotal());

            // Obtener la primera imagen si la lista no está vacía
            List<String> imagenes = item.getProducto().getImagenes();
            return dto;
        }).toList();

        carritoService.emptyCarrito(carrito.getId());

        eventPublisher.publishEvent(new PedidoCreatedEvent(this, pedido));

        // Crear y devolver el PedidoResponseDto con los items mapeados manualmente
        PedidoResponseDto pedidoResponseDto = modelMapper.map(pedido, PedidoResponseDto.class);
        pedidoResponseDto.setPedidoItems(pedidoItemResponseDtoList);
        pedidoResponseDto.setEstado(pedido.getEstado());
        pedidoResponseDto.setTotal(pedido.getTotal());

        return pedidoResponseDto;
    }

    public PedidoResponseDto confirmarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Pedido not found"));

        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new IllegalArgumentException("El pedido debe estar en estado PENDIENTE para ser enviado.");
        }

        pedido.setEstado(Estado.CONFIRMADO);
        pedidoRepository.save(pedido);

        // Publicar evento de envío de pedido
        eventPublisher.publishEvent(new PedidoConfirmadoEvent(this, pedido));

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

    public List<PedidoResponseDto> getMePedidos(){
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

        List<Pedido> pedidos= pedidoRepository.findByUsuario_Id(usuario.getId());

        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoResponseDto.class)).toList();
    }
}
