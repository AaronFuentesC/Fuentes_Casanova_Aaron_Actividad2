package com.aaronfuentescasanova.actividad2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaDTO {
    private Long id;
    private String nombre;
    private Integer capacidad;
    private Boolean esAulaDeOrdenadores;
    private Integer numeroOrdenadores;
}
