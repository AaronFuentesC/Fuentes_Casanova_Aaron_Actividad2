package com.aaronfuentescasanova.actividad2.repository;

import com.aaronfuentescasanova.actividad2.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    List<Aula> findByCapacidadGreaterThanEqual(Integer capacidad);

    List<Aula> findByEsAulaDeOrdenadoresTrue();
}
