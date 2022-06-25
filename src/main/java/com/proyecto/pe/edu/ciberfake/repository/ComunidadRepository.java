package com.proyecto.pe.edu.ciberfake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.pe.edu.ciberfake.model.Comunidad;

import java.util.Optional;

@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {
    Optional<Comunidad> findByComIdAndComEstado(Long comId, Boolean comEstado);
}
