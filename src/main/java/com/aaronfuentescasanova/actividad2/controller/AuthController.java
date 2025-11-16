package com.aaronfuentescasanova.actividad2.controller;

import com.aaronfuentescasanova.actividad2.dto.AuthResponse;
import com.aaronfuentescasanova.actividad2.dto.LoginRequest;
import com.aaronfuentescasanova.actividad2.dto.RegisterRequest;
import com.aaronfuentescasanova.actividad2.dto.UsuarioDTO;
import com.aaronfuentescasanova.actividad2.service.AuthService;
import com.aaronfuentescasanova.actividad2.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request, authenticationManager);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> getPerfil() {
        UsuarioDTO usuario = usuarioService.getPerfil();
        return ResponseEntity.ok(usuario);
    }
}