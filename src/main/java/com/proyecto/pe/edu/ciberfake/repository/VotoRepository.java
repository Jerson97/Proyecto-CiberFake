package com.proyecto.pe.edu.ciberfake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import com.proyecto.pe.edu.ciberfake.model.Voto;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findTopByPostAndUsuarioOrderByVotIdDesc(Post post, Usuario usuario);
}
