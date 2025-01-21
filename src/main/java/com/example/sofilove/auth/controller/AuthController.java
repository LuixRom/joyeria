package com.example.sofilove.auth.controller;

import com.example.sofilove.Usuario.dto.UsuarioRequestDto;
import com.example.sofilove.auth.domain.AuthService;
import com.example.sofilove.auth.dtos.JwtAuthResponse;
import com.example.sofilove.auth.dtos.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthService authenticationService;

    @Autowired
    AuthController(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@Valid @RequestBody UsuarioRequestDto request) {
        JwtAuthResponse response = authenticationService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request) {
        JwtAuthResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok(response);
    }
}
