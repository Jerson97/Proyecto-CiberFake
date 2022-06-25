package com.proyecto.pe.edu.ciberfake.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.pe.edu.ciberfake.dto.PostRequest;
import com.proyecto.pe.edu.ciberfake.dto.PostResponse;
import com.proyecto.pe.edu.ciberfake.service.PostService;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearpost(@RequestBody PostRequest postRequest) {
        postService.crear(postRequest);
        return new ResponseEntity<>("Post creado exitosamente!!!",HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarpost(@RequestBody PostRequest postRequest) {
        postService.actualizar(postRequest);
        return new ResponseEntity<>("Post actualizado exitosamente!!!",HttpStatus.CREATED);
    }

    @GetMapping("/obtener-todos")
    public ResponseEntity<List<PostResponse>> obtenerPosts() {
        return status(HttpStatus.OK).body(postService.obtenerPosts());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<PostResponse> obtenerPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.obtenerPost(id));
    }

    @GetMapping("/por-comunidad/{id}")
    public ResponseEntity<List<PostResponse>> obtenerPostPorComunidad(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.obtenerPostPorComunidad(id));
    }

    @GetMapping("/por-usuario/{usuNombre}")
    public ResponseEntity<List<PostResponse>> obtenerPostPorUsuario(@PathVariable String usuNombre) {
        return status(HttpStatus.OK).body(postService.obtenerPostPorUsuario(usuNombre));
    }
}
