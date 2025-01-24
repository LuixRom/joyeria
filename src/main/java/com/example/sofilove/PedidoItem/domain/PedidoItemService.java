package com.example.sofilove.PedidoItem.domain;

import com.example.sofilove.PedidoItem.dto.PedidoItemResponseDto;
import com.example.sofilove.PedidoItem.infrastructure.PedidoItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PedidoItemService {
    private final PedidoItemRepository pedidoItemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PedidoItemService(PedidoItemRepository pedidoItemRepository, ModelMapper modelMapper) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.modelMapper = modelMapper;
    }



    public List<PedidoItemResponseDto> getPedidoItemByPedidoId(Long pedido_id) {
        // Obtener la lista de PedidoItems
        List<PedidoItem> pedidoItems = pedidoItemRepository.findByPedido_Id(pedido_id);

        // Crear una lista de PedidoItemResponseDto
        List<PedidoItemResponseDto> pedidoItemResponseDtos = new ArrayList<>();

        // Recorrer la lista de PedidoItems y mapear manualmente cada uno
        for (PedidoItem item : pedidoItems) {
            PedidoItemResponseDto dto = new PedidoItemResponseDto();

            // Mapeo manual de cada propiedad
            dto.setProductId(item.getProducto().getId());
            dto.setProductName(item.getProducto().getName());
            dto.setCantidad(item.getCantidad());
            dto.setSubtotal(item.getSubtotal());

            // Añadir el DTO a la lista de respuestas
            pedidoItemResponseDtos.add(dto);
        }

        // Devolver la lista de DTOs
        return pedidoItemResponseDtos;
    }


}
