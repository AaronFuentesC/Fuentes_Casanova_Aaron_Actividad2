package com.aaronfuentescasanova.actividad2.controller;

import com.aaronfuentescasanova.actividad2.dto.TramoHorarioDTO;
import com.aaronfuentescasanova.actividad2.dto.TramoHorarioRequest;
import com.aaronfuentescasanova.actividad2.service.TramoHorarioService;
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
@RequestMapping("/tramo-horario")
@RequiredArgsConstructor
public class TramoHorarioController {

    private final TramoHorarioService tramoHorarioService;

    @GetMapping
    public ResponseEntity<List<TramoHorarioDTO>> getAllTramos() {
        List<TramoHorarioDTO> tramos = tramoHorarioService.getAllTramos();
        return ResponseEntity.ok(tramos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramoHorarioDTO> getTramoById(@PathVariable Long id) {
        TramoHorarioDTO tramo = tramoHorarioService.getTramoById(id);
        return ResponseEntity.ok(tramo);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TramoHorarioDTO> createTramo(@Valid @RequestBody TramoHorarioRequest request) {
        TramoHorarioDTO tramo = tramoHorarioService.createTramo(request);
        return new ResponseEntity<>(tramo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteTramo(@PathVariable Long id) {
        tramoHorarioService.deleteTramo(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Tramo horario eliminado correctamente");
        return ResponseEntity.ok(response);
    }
}