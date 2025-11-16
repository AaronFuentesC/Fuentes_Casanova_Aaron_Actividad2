package com.aaronfuentescasanova.actividad2.controller;

import com.aaronfuentescasanova.actividad2.dto.AulaDTO;
import com.aaronfuentescasanova.actividad2.dto.AulaRequest;
import com.aaronfuentescasanova.actividad2.dto.ReservaDTO;
import com.aaronfuentescasanova.actividad2.service.AulaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaDTO>> getAllAulas(
            @RequestParam(required = false) Integer capacidad,
            @RequestParam(required = false) Boolean ordenadores) {
        
        if (capacidad != null) {
            return ResponseEntity.ok(aulaService.getAulasByCapacidad(capacidad));
        }
        
        if (ordenadores != null && ordenadores) {
            return ResponseEntity.ok(aulaService.getAulasConOrdenadores());
        }
        
        return ResponseEntity.ok(aulaService.getAllAulas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> getAulaById(@PathVariable Long id) {
        AulaDTO aula = aulaService.getAulaById(id);
        return ResponseEntity.ok(aula);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AulaDTO> createAula(@Valid @RequestBody AulaRequest request) {
        AulaDTO aula = aulaService.createAula(request);
        return new ResponseEntity<>(aula, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AulaDTO> updateAula(
            @PathVariable Long id,
            @Valid @RequestBody AulaRequest request) {
        AulaDTO aula = aulaService.updateAula(id, request);
        return ResponseEntity.ok(aula);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteAula(@PathVariable Long id) {
        aulaService.deleteAula(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Aula eliminada correctamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/reservas")
    public ResponseEntity<List<ReservaDTO>> getReservasByAula(@PathVariable Long id) {
        List<ReservaDTO> reservas = aulaService.getReservasByAula(id);
        return ResponseEntity.ok(reservas);
    }
}