package com.proyecto.pe.edu.ciberfake.mapper;

//import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.proyecto.pe.edu.ciberfake.dto.PostRequest;
import com.proyecto.pe.edu.ciberfake.dto.PostResponse;
import com.proyecto.pe.edu.ciberfake.model.*;
import com.proyecto.pe.edu.ciberfake.repository.ComentarioRepository;
import com.proyecto.pe.edu.ciberfake.repository.VotoRepository;
import com.proyecto.pe.edu.ciberfake.service.AuthService;

import java.util.Optional;

import static com.proyecto.pe.edu.ciberfake.model.VotoTipo.DOWNVOTE;
import static com.proyecto.pe.edu.ciberfake.model.VotoTipo.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class  PostMapper {

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "postDescripcion", source = "postRequest.postDescripcion")
    @Mapping(target = "comunidad", source = "comunidad")
    @Mapping(target = "usuario", source = "usuario")
    public abstract Post map(PostRequest postRequest, Comunidad comunidad, Usuario usuario);

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "comNombre", source = "comunidad.comNombre")
    @Mapping(target = "comId", source = "comunidad.comId")
    @Mapping(target = "usuId", source = "usuario.usuId")
    @Mapping(target = "usuNombre", source = "usuario.usuNombre")
    @Mapping(target = "postContComentario", expression = "java(contComentario(post))")
    @Mapping(target = "postDuracion", expression = "java(getDuracion(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer contComentario(Post post){
        return comentarioRepository.findByPostAndComeEstado(post,true).size();
    }

    String getDuracion(Post post){
        return TimeAgo.using(post.getPostFecCrea().toEpochMilli());
    }

    boolean isPostUpVoted(Post post){
        return checkVotoTipo(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post){
        return checkVotoTipo(post, DOWNVOTE);
    }

    private boolean checkVotoTipo(Post post, VotoTipo votoTipo){
        if(authService.estaLogueado()){
            Optional<Voto> votarPorPostUsuario =
                    votoRepository.findTopByPostAndUsuarioOrderByVotIdDesc(post,
                            authService.obtenerUsuarioActual());
            return votarPorPostUsuario.filter(voto -> voto.getVotType().equals(votoTipo))
                    .isPresent();
        }
        return false;
    }
}
