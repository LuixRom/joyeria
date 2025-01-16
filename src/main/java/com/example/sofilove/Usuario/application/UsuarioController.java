package com.example.sofilove.usuario.application;


import com.example.sofilove.usuario.domain.UsuarioService;
import com.example.sofilove.usuario.dto.UsuarioRequestDto;
import com.example.sofilove.usuario.dto.UsuarioResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/user")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUser(@RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.create(usuarioRequestDto);
        return ResponseEntity.ok(usuarioResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.getUserById(id);
        return ResponseEntity.ok(usuarioResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUser(@PathVariable Long id, @RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.update(id, usuarioRequestDto);
        return ResponseEntity.ok(usuarioResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers() {
        List<UsuarioResponseDto> users = usuarioService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
