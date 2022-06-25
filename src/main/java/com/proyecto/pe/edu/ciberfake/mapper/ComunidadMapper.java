package com.proyecto.pe.edu.ciberfake.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.proyecto.pe.edu.ciberfake.dto.ComunidadDto;
import com.proyecto.pe.edu.ciberfake.model.Comunidad;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import com.proyecto.pe.edu.ciberfake.repository.ComentarioRepository;
import com.proyecto.pe.edu.ciberfake.repository.PostRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ComunidadMapper {

    @Autowired
    private PostRepository postRepository;


    @Mapping(target = "comNumeroPosts", expression = "java(contPosts(comunidad))")
    @Mapping(target = "usuNombre", expression = "java(comunidad.getUsuario().getUsuNombre())")
    public abstract ComunidadDto mapComunidadToDto(Comunidad comunidad);

    @InheritInverseConfiguration
    @Mapping(target = "usuario", source = "usuario")
    public abstract Comunidad mapDtoToComunidad(ComunidadDto comunidadDto, Usuario usuario);

    Integer contPosts(Comunidad comunidad){
        return postRepository.findAllByComunidadAndPostEstado(comunidad, true).size();
    }

}
