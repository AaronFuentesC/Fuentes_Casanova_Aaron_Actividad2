package com.aaronfuentescasanova.actividad2.dto;

import com.aaronfuentescasanova.actividad2.enums.DiaSemana;
import com.aaronfuentescasanova.actividad2.enums.TipoTramo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TramoHorarioDTO {
    private Long id;
    private DiaSemana diaSemana;
    private Integer sesionDia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private TipoTramo tipo;
}
