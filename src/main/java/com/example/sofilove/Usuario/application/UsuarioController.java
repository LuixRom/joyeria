package com.example.sofilove.Usuario.application;


import com.example.sofilove.Usuario.domain.UsuarioService;
import com.example.sofilove.Usuario.dto.UsuarioRequestDto;
import com.example.sofilove.Usuario.dto.UsuarioResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        usuarioService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long userId) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.getUserById(userId);
        return ResponseEntity.ok(usuarioResponseDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UsuarioResponseDto> updateUser(@PathVariable Long userId, @RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto usuarioResponseDto = usuarioService.update(userId, usuarioRequestDto);
        return ResponseEntity.ok(usuarioResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers() {
        List<UsuarioResponseDto> users = usuarioService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
