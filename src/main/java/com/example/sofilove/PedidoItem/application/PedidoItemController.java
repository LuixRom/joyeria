package com.example.sofilove.PedidoItem.application;

import com.example.sofilove.PedidoItem.domain.PedidoItemService;
import com.example.sofilove.PedidoItem.dto.PedidoItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("pedidoItem")
public class PedidoItemController {

    private final PedidoItemService pedidoItemService;

    @Autowired
    public PedidoItemController(PedidoItemService pedidoItemService) {
        this.pedidoItemService = pedidoItemService;
    }

    @GetMapping("{pedidoId}")
    public ResponseEntity<List<PedidoItemResponseDto>> getPedidoItemByPedidoId(@PathVariable Long pedidoId) {
        List<PedidoItemResponseDto> pedidoItem = pedidoItemService.getPedidoItemByPedidoId(pedidoId);
        return ResponseEntity.ok(pedidoItem);
    }

}
