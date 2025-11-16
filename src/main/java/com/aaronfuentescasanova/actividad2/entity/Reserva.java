package com.aaronfuentescasanova.actividad2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private Integer numeroAsistentes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tramo_horario_id", nullable = false)
    private TramoHorario tramoHorario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
