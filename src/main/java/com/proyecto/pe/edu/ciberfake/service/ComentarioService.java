package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.proyecto.pe.edu.ciberfake.dto.ComentarioDto;
import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;
import com.proyecto.pe.edu.ciberfake.exceptions.PostNotFoundException;
import com.proyecto.pe.edu.ciberfake.mapper.ComentarioMapper;
import com.proyecto.pe.edu.ciberfake.model.Comentario;
import com.proyecto.pe.edu.ciberfake.model.EmailNotificacion;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import com.proyecto.pe.edu.ciberfake.repository.ComentarioRepository;
import com.proyecto.pe.edu.ciberfake.repository.PostRepository;
import com.proyecto.pe.edu.ciberfake.repository.UsuarioRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class ComentarioService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthService authService;
    private final ComentarioMapper comentarioMapper;
    private final ComentarioRepository comentarioRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void crear(ComentarioDto comentarioDto){
        comentarioDto.setComeEstado(true);
        Post post = postRepository.findById(comentarioDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(comentarioDto.getPostId().toString()));
        Comentario comentario = comentarioMapper.map(comentarioDto, post, authService.obtenerUsuarioActual());
        
        comentarioRepository.save(comentario);

        String mensaje = post.getUsuario().getUsuNombre() + " comento tu post. " + POST_URL;
        enviarNotificacion(mensaje, post.getUsuario());
    }

    private void enviarNotificacion(String mensaje, Usuario usuario){
        mailService.enviarMail(new EmailNotificacion(usuario.getUsuNombre() + " comento en tu post", usuario.getUsuEmail(), mensaje));
    }

    public List<ComentarioDto> obtenerComentariosPorPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return comentarioRepository.findByPostAndComeEstado(post, true)
                .stream()
                .map(comentarioMapper::mapToDto).collect(toList());
    }

    public List<ComentarioDto> obtenerComentariosPorUsuario(String usuNombre) {
        Usuario usuario = usuarioRepository.findByUsuNombreAndUsuEstado(usuNombre, true)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con nombre :"+usuNombre));
        return comentarioRepository.findAllByUsuarioAndComeEstado(usuario, true)
                .stream()
                .map(comentarioMapper::mapToDto)
                .collect(toList());
    }

    public void borrarComentario(Long comeId) {
        Comentario comentario = comentarioRepository.findById(comeId).orElseThrow(() -> new CiberFakeException("El comentario no existe"));
        comentario.setComeEstado(false);
        comentarioRepository.save(comentario);
    }
}
