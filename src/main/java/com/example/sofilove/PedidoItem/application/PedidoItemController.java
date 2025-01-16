package com.example.sofilove.pedidoItem.application;

import com.example.sofilove.pedidoItem.domain.PedidoItemService;
import com.example.sofilove.pedidoItem.dto.PedidoItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("pedidoItem")
public class PedidoItemController {

    private final PedidoItemService pedidoItemService;

    @Autowired
    public PedidoItemController(PedidoItemService pedidoItemService) {
        this.pedidoItemService = pedidoItemService;
    }

    @GetMapping("{pedidoId}")
    public ResponseEntity<PedidoItemResponseDto> getPedidoItemByPedidoId(@PathVariable Long pedidoId) {
        PedidoItemResponseDto pedidoItem = pedidoItemService.getPedidoItemByPedidoId(pedidoId);
        return ResponseEntity.ok(pedidoItem);
    }

}
