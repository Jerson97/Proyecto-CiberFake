package com.proyecto.pe.edu.ciberfake.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComunidadDto {
    private Long comId;
    private String comNombre;
    private String comDescripcion;
    private String usuNombre;
    private Integer comNumeroPosts;
    private Boolean comEstado;
}
