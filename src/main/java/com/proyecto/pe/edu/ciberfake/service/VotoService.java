package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyecto.pe.edu.ciberfake.dto.VotoDto;
import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;
import com.proyecto.pe.edu.ciberfake.exceptions.PostNotFoundException;
import com.proyecto.pe.edu.ciberfake.model.Post;
import com.proyecto.pe.edu.ciberfake.model.Voto;
import com.proyecto.pe.edu.ciberfake.repository.PostRepository;
import com.proyecto.pe.edu.ciberfake.repository.VotoRepository;

import java.util.Optional;

import static com.proyecto.pe.edu.ciberfake.model.VotoTipo.UPVOTE;

@Service
@AllArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void votar(VotoDto votoDto) {
        Post post = postRepository.findById(votoDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post con ID - " + votoDto.getPostId() + " no se encontro."));
        Optional<Voto> votarPorPostUsuario = votoRepository.findTopByPostAndUsuarioOrderByVotIdDesc(post, authService.obtenerUsuarioActual());
        if (votarPorPostUsuario.isPresent() &&
                votarPorPostUsuario.get().getVotType()
                        .equals(votoDto.getVotoTipo())) {
            throw new CiberFakeException("Ya has votado "
                    + votoDto.getVotoTipo() + " a este post");
        }
        if (UPVOTE.equals(votoDto.getVotoTipo())) {
            post.setPostContVoto(post.getPostContVoto() + 1);
        } else {
            post.setPostContVoto(post.getPostContVoto() - 1);
        }
        votoRepository.save(mapToVote(votoDto, post));
        postRepository.save(post);
    }

    private Voto mapToVote(VotoDto votoDto, Post post) {
        return Voto.builder()
                .votType(votoDto.getVotoTipo())
                .post(post)
                .usuario(authService.obtenerUsuarioActual())
                .build();
    }
}
