package com.example.sofilove.PedidoItem.application;

import com.example.sofilove.PedidoItem.domain.PedidoItemService;
import com.example.sofilove.PedidoItem.dto.PedidoItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pedidoItem")
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
