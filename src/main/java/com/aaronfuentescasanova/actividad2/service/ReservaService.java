package com.aaronfuentescasanova.actividad2.service;

import com.aaronfuentescasanova.actividad2.dto.ReservaDTO;
import com.aaronfuentescasanova.actividad2.dto.ReservaRequest;
import com.aaronfuentescasanova.actividad2.entity.Aula;
import com.aaronfuentescasanova.actividad2.entity.Reserva;
import com.aaronfuentescasanova.actividad2.entity.TramoHorario;
import com.aaronfuentescasanova.actividad2.entity.Usuario;
import com.aaronfuentescasanova.actividad2.exception.ReservaException;
import com.aaronfuentescasanova.actividad2.exception.ResourceNotFoundException;
import com.aaronfuentescasanova.actividad2.exception.UnauthorizedException;
import com.aaronfuentescasanova.actividad2.repository.AulaRepository;
import com.aaronfuentescasanova.actividad2.repository.ReservaRepository;
import com.aaronfuentescasanova.actividad2.repository.TramoHorarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final AulaRepository aulaRepository;
    private final TramoHorarioRepository tramoHorarioRepository;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public List<ReservaDTO> getAllReservas() {
        return reservaRepository.findAll().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    public ReservaDTO getReservaById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return modelMapper.map(reserva, ReservaDTO.class);
    }

    @Transactional
    public ReservaDTO createReserva(ReservaRequest request) {
        if (request.getFecha().isBefore(LocalDate.now())) {
            throw new ReservaException("No se pueden hacer reservas en el pasado");
        }

        Aula aula = aulaRepository.findById(request.getAulaId())
                .orElseThrow(() -> new ResourceNotFoundException("Aula no encontrada"));

        TramoHorario tramo = tramoHorarioRepository.findById(request.getTramoHorarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Tramo horario no encontrado"));

        // Validar capacidad
        if (request.getNumeroAsistentes() > aula.getCapacidad()) {
            throw new ReservaException("El número de asistentes (" + request.getNumeroAsistentes() + 
                    ") supera la capacidad del aula (" + aula.getCapacidad() + ")");
        }

        List<Reserva> reservasExistentes = reservaRepository.findByAulaAndTramoAndFecha(
                request.getAulaId(),
                request.getTramoHorarioId(),
                request.getFecha()
        );

        if (!reservasExistentes.isEmpty()) {
            throw new ReservaException("Ya existe una reserva para esta aula, tramo horario y fecha");
        }

        Usuario usuario = usuarioService.getCurrentUser();
        
        Reserva reserva = new Reserva();
        reserva.setFecha(request.getFecha());
        reserva.setMotivo(request.getMotivo());
        reserva.setNumeroAsistentes(request.getNumeroAsistentes());
        reserva.setAula(aula);
        reserva.setTramoHorario(tramo);
        reserva.setUsuario(usuario);

        Reserva savedReserva = reservaRepository.save(reserva);
        return modelMapper.map(savedReserva, ReservaDTO.class);
    }

    @Transactional
    public void deleteReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));

        Usuario currentUser = usuarioService.getCurrentUser();

        if (!reserva.getUsuario().getId().equals(currentUser.getId()) &&
            !currentUser.getRole().name().equals("ROLE_ADMIN")) {
            throw new UnauthorizedException("No tienes permiso para eliminar esta reserva");
        }

        reservaRepository.delete(reserva);
    }
}