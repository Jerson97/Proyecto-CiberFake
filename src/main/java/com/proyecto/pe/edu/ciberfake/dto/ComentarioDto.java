package com.proyecto.pe.edu.ciberfake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDto {
    private Long comeId;
    private Long postId;
    private String comeTexto;
    private String usuNombre;
    private Boolean comeEstado;
}
