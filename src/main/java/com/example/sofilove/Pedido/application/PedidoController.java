package com.example.sofilove.Pedido.application;

import com.example.sofilove.Pedido.domain.PedidoService;
import com.example.sofilove.Pedido.dto.PedidoRequestDto;
import com.example.sofilove.Pedido.dto.PedidoResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoservice;

    @Autowired
    public PedidoController(PedidoService pedidoservice) {
        this.pedidoservice = pedidoservice;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PedidoResponseDto> createPedido(@PathVariable Long userId,  @Valid @RequestBody PedidoRequestDto pedidoRequestDto) {
        PedidoResponseDto pedido = pedidoservice.crearPedido(userId, pedidoRequestDto);
        return ResponseEntity.created(null).body(pedido);
    }

    @GetMapping("/")
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
