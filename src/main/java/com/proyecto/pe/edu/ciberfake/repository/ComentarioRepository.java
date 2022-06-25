package com.proyecto.pe.edu.ciberfake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.pe.edu.ciberfake.model.Comentario;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPostAndComeEstado(Post post, Boolean comeEstado);

    List<Comentario> findAllByUsuarioAndComeEstado(Usuario usuario, Boolean comeEstado);
}
