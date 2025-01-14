package com.example.sofilove.Pedido.application;

import com.example.sofilove.Pedido.domain.Pedidoservice;
import com.example.sofilove.Pedido.dto.PedidoRequestDto;
import com.example.sofilove.Pedido.dto.PedidoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/pedido")
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

    @GetMapping("{id}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Long id) {
        PedidoResponseDto pedido = pedidoservice.getPedidoById(id);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PedidoResponseDto> deletePedido(@PathVariable Long id) {
        pedidoservice.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}
