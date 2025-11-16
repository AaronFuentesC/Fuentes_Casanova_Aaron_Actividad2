package com.aaronfuentescasanova.actividad2.service;

import com.aaronfuentescasanova.actividad2.dto.TramoHorarioDTO;
import com.aaronfuentescasanova.actividad2.dto.TramoHorarioRequest;
import com.aaronfuentescasanova.actividad2.entity.TramoHorario;
import com.aaronfuentescasanova.actividad2.exception.ResourceNotFoundException;
import com.aaronfuentescasanova.actividad2.repository.TramoHorarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TramoHorarioService {

    private final TramoHorarioRepository tramoHorarioRepository;
    private final ModelMapper modelMapper;

    public List<TramoHorarioDTO> getAllTramos() {
        return tramoHorarioRepository.findAll().stream()
                .map(tramo -> modelMapper.map(tramo, TramoHorarioDTO.class))
                .collect(Collectors.toList());
    }

    public TramoHorarioDTO getTramoById(Long id) {
        TramoHorario tramo = tramoHorarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tramo horario no encontrado"));
        return modelMapper.map(tramo, TramoHorarioDTO.class);
    }

    @Transactional
    public TramoHorarioDTO createTramo(TramoHorarioRequest request) {
        TramoHorario tramo = modelMapper.map(request, TramoHorario.class);
        TramoHorario savedTramo = tramoHorarioRepository.save(tramo);
        return modelMapper.map(savedTramo, TramoHorarioDTO.class);
    }

    @Transactional
    public void deleteTramo(Long id) {
        TramoHorario tramo = tramoHorarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tramo horario no encontrado"));
        tramoHorarioRepository.delete(tramo);
    }
}