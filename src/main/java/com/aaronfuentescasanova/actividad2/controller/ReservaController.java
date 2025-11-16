package com.aaronfuentescasanova.actividad2.controller;

import com.aaronfuentescasanova.actividad2.dto.ReservaDTO;
import com.aaronfuentescasanova.actividad2.dto.ReservaRequest;
import com.aaronfuentescasanova.actividad2.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas() {
        List<ReservaDTO> reservas = reservaService.getAllReservas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id) {
        ReservaDTO reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> createReserva(@Valid @RequestBody ReservaRequest request) {
        ReservaDTO reserva = reservaService.createReserva(request);
        return new ResponseEntity<>(reserva, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Reserva eliminada correctamente");
        return ResponseEntity.ok(response);
    }
}