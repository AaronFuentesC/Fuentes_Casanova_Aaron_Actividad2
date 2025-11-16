package com.aaronfuentescasanova.actividad2.repository;

import com.aaronfuentescasanova.actividad2.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByAulaId(Long aulaId);

    @Query("SELECT r FROM Reserva r WHERE r.aula.id = :aulaId AND r.tramoHorario.id = :tramoId AND r.fecha = :fecha")
    List<Reserva> findByAulaAndTramoAndFecha(
            @Param("aulaId") Long aulaId,
            @Param("tramoId") Long tramoId,
            @Param("fecha") LocalDate fecha
    );
}
