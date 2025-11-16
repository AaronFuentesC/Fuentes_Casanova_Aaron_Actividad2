package com.aaronfuentescasanova.actividad2.repository;

import com.aaronfuentescasanova.actividad2.entity.TramoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TramoHorarioRepository extends JpaRepository<TramoHorario, Long> {
}
