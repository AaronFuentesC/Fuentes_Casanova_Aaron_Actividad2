package com.aaronfuentescasanova.actividad2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private LocalDate fecha;
    private String motivo;
    private Integer numeroAsistentes;
    private LocalDate fechaCreacion;
    private AulaDTO aula;
    private TramoHorarioDTO tramoHorario;
    private UsuarioDTO usuario;
}
