package com.aaronfuentescasanova.actividad2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaRequest {
    
    @NotBlank(message = "El nombre del aula es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;
    
    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacidad;
    
    @NotNull(message = "Debe indicar si es aula de ordenadores")
    private Boolean esAulaDeOrdenadores;
    
    @Min(value = 0, message = "El número de ordenadores no puede ser negativo")
    private Integer numeroOrdenadores;
}

