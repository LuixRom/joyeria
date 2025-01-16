package com.example.sofilove.pedidoItem.domain;

import com.example.sofilove.pedidoItem.dto.PedidoItemResponseDto;
import com.example.sofilove.pedidoItem.infrastructure.PedidoItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PedidoItemService {
    private final PedidoItemRepository pedidoItemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PedidoItemService(PedidoItemRepository pedidoItemRepository, ModelMapper modelMapper) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.modelMapper = modelMapper;
    }

    public PedidoItemResponseDto getPedidoItemByPedidoId(Long pedido_id) {
        PedidoItem pedidoItem = pedidoItemRepository.findByPedido(pedido_id);

        return modelMapper.map(pedidoItem, PedidoItemResponseDto.class);
    }


}
