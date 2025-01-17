package com.example.sofilove.Pedido.application;

import com.example.sofilove.Pedido.domain.Pedidoservice;
import com.example.sofilove.Pedido.dto.PedidoRequestDto;
import com.example.sofilove.Pedido.dto.PedidoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final Pedidoservice pedidoservice;

    @Autowired
    public PedidoController(Pedidoservice pedidoservice) {
        this.pedidoservice = pedidoservice;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDto> createPedido(PedidoRequestDto pedidoRequestDto) {
        PedidoResponseDto pedido = pedidoservice.crearPedido(pedidoRequestDto);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getPedidos() {
        List<PedidoResponseDto> pedidos = pedidoservice.getallPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("{pedidoId}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Long pedidoId) {
        PedidoResponseDto pedido = pedidoservice.getPedidoById(pedidoId);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("{pedidoId}")
    public ResponseEntity<PedidoResponseDto> deletePedido(@PathVariable Long pedidoId) {
        pedidoservice.deletePedido(pedidoId);
        return ResponseEntity.noContent().build();
    }

}
