package com.proyecto.pe.edu.ciberfake.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.proyecto.pe.edu.ciberfake.dto.ComentarioDto;
import com.proyecto.pe.edu.ciberfake.model.Comentario;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Usuario;



@Mapper(componentModel = "spring")
public interface ComentarioMapper {

	@Mapping(target = "comeId", ignore = true)
	@Mapping(target = "comeTexto", source = "comentarioDto.comeTexto")
	@Mapping(target = "post", source = "post")
    @Mapping(target = "usuario", source = "usuario")
	
    Comentario map(ComentarioDto comentarioDto, Post post, Usuario usuario);

    @Mapping(target = "postId", expression = "java(comentario.getPost().getPostId())")
    @Mapping(target = "usuNombre", expression = "java(comentario.getUsuario().getUsuNombre())")
    ComentarioDto mapToDto(Comentario comentario);
	
}
