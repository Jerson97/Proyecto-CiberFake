package com.proyecto.pe.edu.ciberfake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.pe.edu.ciberfake.model.Comunidad;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Usuario;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByComunidadAndPostEstado(Comunidad comunidad, Boolean postEstado);

    List<Post> findByUsuarioAndPostEstado(Usuario usuario, Boolean postEstado);
}
