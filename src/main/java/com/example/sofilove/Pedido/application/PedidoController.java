package com.example.sofilove.pedido.application;

import com.example.sofilove.pedido.domain.PedidoService;
import com.example.sofilove.pedido.dto.PedidoRequestDto;
import com.example.sofilove.pedido.dto.PedidoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> createPedido(PedidoRequestDto pedidoRequestDto) {
        PedidoResponseDto pedido = pedidoService.crearPedido(pedidoRequestDto);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getPedidos() {
        List<PedidoResponseDto> pedidos = pedidoService.getallPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("{id}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Long id) {
        PedidoResponseDto pedido = pedidoService.getPedidoById(id);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PedidoResponseDto> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}
