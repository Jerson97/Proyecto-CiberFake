package com.proyecto.pe.edu.ciberfake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.pe.edu.ciberfake.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuNombreAndUsuEstado(String usuNombre, Boolean usuEstado);
    Optional<Usuario> findByUsuNombre(String usuNombre);
}
