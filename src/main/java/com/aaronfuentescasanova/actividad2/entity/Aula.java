package com.aaronfuentescasanova.actividad2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "aulas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Boolean esAulaDeOrdenadores;

    private Integer numeroOrdenadores;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reserva> reservas;
}