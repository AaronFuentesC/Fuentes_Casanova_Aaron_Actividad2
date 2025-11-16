package com.aaronfuentescasanova.actividad2.service;

import com.aaronfuentescasanova.actividad2.dto.AulaDTO;
import com.aaronfuentescasanova.actividad2.dto.AulaRequest;
import com.aaronfuentescasanova.actividad2.dto.ReservaDTO;
import com.aaronfuentescasanova.actividad2.entity.Aula;
import com.aaronfuentescasanova.actividad2.exception.ResourceNotFoundException;
import com.aaronfuentescasanova.actividad2.repository.AulaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepository;
    private final ModelMapper modelMapper;

    public List<AulaDTO> getAllAulas() {
        return aulaRepository.findAll().stream()
                .map(aula -> modelMapper.map(aula, AulaDTO.class))
                .collect(Collectors.toList());
    }

    public AulaDTO getAulaById(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aula no encontrada"));
        return modelMapper.map(aula, AulaDTO.class);
    }

    @Transactional
    public AulaDTO createAula(AulaRequest request) {
        Aula aula = modelMapper.map(request, Aula.class);
        Aula savedAula = aulaRepository.save(aula);
        return modelMapper.map(savedAula, AulaDTO.class);
    }

    @Transactional
    public AulaDTO updateAula(Long id, AulaRequest request) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aula no encontrada"));

        aula.setNombre(request.getNombre());
        aula.setCapacidad(request.getCapacidad());
        aula.setEsAulaDeOrdenadores(request.getEsAulaDeOrdenadores());
        aula.setNumeroOrdenadores(request.getNumeroOrdenadores());

        Aula updatedAula = aulaRepository.save(aula);
        return modelMapper.map(updatedAula, AulaDTO.class);
    }

    @Transactional
    public void deleteAula(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aula no encontrada"));
        aulaRepository.delete(aula);
    }

    public List<ReservaDTO> getReservasByAula(Long id) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aula no encontrada"));
        
        return aula.getReservas().stream()
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class))
                .collect(Collectors.toList());
    }

    public List<AulaDTO> getAulasByCapacidad(Integer capacidad) {
        return aulaRepository.findByCapacidadGreaterThanEqual(capacidad).stream()
                .map(aula -> modelMapper.map(aula, AulaDTO.class))
                .collect(Collectors.toList());
    }

    public List<AulaDTO> getAulasConOrdenadores() {
        return aulaRepository.findByEsAulaDeOrdenadoresTrue().stream()
                .map(aula -> modelMapper.map(aula, AulaDTO.class))
                .collect(Collectors.toList());
    }
}