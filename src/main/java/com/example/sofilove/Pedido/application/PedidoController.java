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

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PedidoResponseDto> createPedido(@PathVariable Long userId,  @Valid @RequestBody PedidoRequestDto pedidoRequestDto) {
        PedidoResponseDto pedido = pedidoService.crearPedido(userId, pedidoRequestDto);
        return ResponseEntity.created(null).body(pedido);
    }

    @GetMapping("/")
    public ResponseEntity<List<PedidoResponseDto>> getPedidos() {
        List<PedidoResponseDto> pedidos = pedidoService.getallPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("{pedidoId}")
    public ResponseEntity<PedidoResponseDto> getPedidoById(@PathVariable Long pedidoId) {
        PedidoResponseDto pedido = pedidoService.getPedidoById(pedidoId);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("{pedidoId}")
    public ResponseEntity<PedidoResponseDto> deletePedido(@PathVariable Long pedidoId) {
        pedidoService.deletePedido(pedidoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<PedidoResponseDto>> getMe() {
        List<PedidoResponseDto> pedidos = pedidoService.getMePedidos();
        return ResponseEntity.ok(pedidos);
    }

}
