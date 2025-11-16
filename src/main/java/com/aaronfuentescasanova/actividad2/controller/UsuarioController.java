package com.aaronfuentescasanova.actividad2.controller;

import com.aaronfuentescasanova.actividad2.dto.CambiarPasswordRequest;
import com.aaronfuentescasanova.actividad2.dto.UpdateUsuarioRequest;
import com.aaronfuentescasanova.actividad2.dto.UsuarioDTO;
import com.aaronfuentescasanova.actividad2.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario eliminado correctamente");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUsuarioRequest request) {
        UsuarioDTO usuario = usuarioService.updateUsuario(id, request);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/cambiar-pass")
    public ResponseEntity<Map<String, String>> cambiarPassword(
            @Valid @RequestBody CambiarPasswordRequest request) {
        usuarioService.cambiarPassword(request);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Contraseña cambiada correctamente");
        return ResponseEntity.ok(response);
    }
}