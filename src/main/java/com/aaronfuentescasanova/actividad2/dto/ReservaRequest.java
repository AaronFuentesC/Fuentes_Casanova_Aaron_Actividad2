package com.aaronfuentescasanova.actividad2.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequest {
    
    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha debe ser futura")
    private LocalDate fecha;
    
    @NotBlank(message = "El motivo es obligatorio")
    @Size(min = 3, max = 500, message = "El motivo debe tener entre 3 y 500 caracteres")
    private String motivo;
    
    @NotNull(message = "El número de asistentes es obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 asistente")
    private Integer numeroAsistentes;
    
    @NotNull(message = "El ID del aula es obligatorio")
    private Long aulaId;
    
    @NotNull(message = "El ID del tramo horario es obligatorio")
    private Long tramoHorarioId;
}

