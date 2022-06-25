package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyecto.pe.edu.ciberfake.dto.PostRequest;
import com.proyecto.pe.edu.ciberfake.dto.PostResponse;
import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;
import com.proyecto.pe.edu.ciberfake.exceptions.ComunidadNotFoundException;
import com.proyecto.pe.edu.ciberfake.exceptions.PostNotFoundException;
import com.proyecto.pe.edu.ciberfake.mapper.PostMapper;
import com.proyecto.pe.edu.ciberfake.model.Comunidad;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Usuario;
import com.proyecto.pe.edu.ciberfake.repository.ComunidadRepository;
import com.proyecto.pe.edu.ciberfake.repository.PostRepository;
import com.proyecto.pe.edu.ciberfake.repository.UsuarioRepository;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final ComunidadRepository comunidadRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void crear(PostRequest postRequest) {
        postRequest.setPostEstado(true);
        Comunidad comunidad = comunidadRepository.findByComIdAndComEstado(postRequest.getComId(), true)
                .orElseThrow(() -> new ComunidadNotFoundException(postRequest.getComId().toString()));
        postRepository.save(postMapper.map(postRequest, comunidad, authService.obtenerUsuarioActual()));
    }

    @Transactional(readOnly = true)
    public PostResponse obtenerPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> obtenerPosts() {
        List<PostResponse> listaRetornar = postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
        Collections.reverse(listaRetornar);
        return listaRetornar;
    }

    @Transactional(readOnly = true)
    public List<PostResponse> obtenerPostPorComunidad(Long id) {
        Comunidad comunidad = comunidadRepository.findById(id)
                .orElseThrow(() -> new ComunidadNotFoundException(id.toString()));
        List<Post> posts = postRepository.findAllByComunidadAndPostEstado(comunidad, true);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> obtenerPostPorUsuario(String usuNombre) {
        Usuario usuario = usuarioRepository.findByUsuNombreAndUsuEstado(usuNombre, true)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro Post con nombre: "+usuNombre));
        log.info("usuario: {}",usuario);
        return postRepository.findByUsuarioAndPostEstado(usuario, true)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public void actualizar(PostRequest postRequest) {
        Post postDB = postRepository.findById(postRequest.getPostId()).orElseThrow(() -> new CiberFakeException("No se encontro Post con Id: "+postRequest.getPostId()));
        postRequest.setPostContVoto(postDB.getPostContVoto());
        Comunidad comunidad = comunidadRepository.findByComIdAndComEstado(postRequest.getComId(), true)
                .orElseThrow(() -> new ComunidadNotFoundException(postRequest.getComId().toString()));
        postRepository.save(postMapper.map(postRequest, comunidad, authService.obtenerUsuarioActual()));
    }
}
