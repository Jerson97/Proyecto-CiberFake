package com.proyecto.pe.edu.ciberfake.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proyecto.pe.edu.ciberfake.dto.VotoDto;
import com.proyecto.pe.edu.ciberfake.service.VotoService;

@RestController
@RequestMapping("/api/voto")
@AllArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VotoDto votoDto) {
        votoService.votar(votoDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
