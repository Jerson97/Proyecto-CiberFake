package com.proyecto.pe.edu.ciberfake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private Long comId;
    private String postNombre;
    private String postUrl;
    private String postDescripcion;
    private Integer postContVoto;
    private Boolean postEstado;
}
