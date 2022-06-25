package com.proyecto.pe.edu.ciberfake.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.pe.edu.ciberfake.dto.ComunidadDto;
import com.proyecto.pe.edu.ciberfake.service.ComunidadService;

import java.util.List;

@RestController
@RequestMapping("/api/comunidad")
@AllArgsConstructor
@Slf4j
public class ComunidadController {

    private final ComunidadService comunidadService;

    @PostMapping("/crear")
    public ResponseEntity<ComunidadDto> crearComunidad(@RequestBody ComunidadDto comunidadDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comunidadService.guardar(comunidadDto));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ComunidadDto> actualizarComunidad(@RequestBody ComunidadDto comunidadDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comunidadService.actualizar(comunidadDto));
    }

    @GetMapping("/obtener-todos")
    public ResponseEntity<List<ComunidadDto>> obtenerComunidades(){
        return ResponseEntity.status(HttpStatus.OK).body(comunidadService.obtenerComunidades());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ComunidadDto> obtenerComunidad(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(comunidadService.obtenerComunidad(id));
    }
}
