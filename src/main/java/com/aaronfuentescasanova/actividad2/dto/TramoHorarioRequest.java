package com.aaronfuentescasanova.actividad2.dto;

import com.aaronfuentescasanova.actividad2.enums.DiaSemana;
import com.aaronfuentescasanova.actividad2.enums.TipoTramo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TramoHorarioRequest {
    
    @NotNull(message = "El día de la semana es obligatorio")
    private DiaSemana diaSemana;
    
    @NotNull(message = "La sesión del día es obligatoria")
    @Min(value = 1, message = "La sesión debe ser al menos 1")
    private Integer sesionDia;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;
    
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;
    
    @NotNull(message = "El tipo de tramo es obligatorio")
    private TipoTramo tipo;
}

