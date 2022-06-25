package com.proyecto.pe.edu.ciberfake.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.pe.edu.ciberfake.dto.ComentarioDto;
import com.proyecto.pe.edu.ciberfake.service.ComentarioService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comentario")
@AllArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearComentario(@RequestBody ComentarioDto comentarioDto){
        comentarioService.crear(comentarioDto);
        return new ResponseEntity<>("El comentario fue creado exitosamente",CREATED);
    }

    @GetMapping("/por-post/{postId}")
    public ResponseEntity<List<ComentarioDto>> obtenerComentariosPorPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(comentarioService.obtenerComentariosPorPost(postId));
    }

    @GetMapping("/por-usuario/{usuNombre}")
    public ResponseEntity<List<ComentarioDto>> obtenerComentariosPorUsuario(@PathVariable String usuNombre){
        return ResponseEntity.status(OK)
                .body(comentarioService.obtenerComentariosPorUsuario(usuNombre));
    }

    @DeleteMapping("/borrar/{comeId}")
    public ResponseEntity<String> borrarComentario(@PathVariable Long comeId){
        comentarioService.borrarComentario(comeId);
        return ResponseEntity.status(OK).body("El comentario fue borrado exitosamente");
    }
}
