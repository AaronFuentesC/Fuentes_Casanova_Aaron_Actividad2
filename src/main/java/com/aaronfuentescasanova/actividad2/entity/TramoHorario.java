package com.aaronfuentescasanova.actividad2.entity;


import com.aaronfuentescasanova.actividad2.enums.DiaSemana;
import com.aaronfuentescasanova.actividad2.enums.TipoTramo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "tramos_horarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TramoHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private Integer sesionDia;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTramo tipo;

    @OneToMany(mappedBy = "tramoHorario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reserva> reservas;
}
