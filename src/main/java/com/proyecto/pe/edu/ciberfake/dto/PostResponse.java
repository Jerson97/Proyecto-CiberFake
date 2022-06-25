package com.proyecto.pe.edu.ciberfake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String postNombre;
    private String postUrl;
    private String postDescripcion;
    private Integer usuId;
    private String usuNombre;
    private String comNombre;
    private Long comId;
    private Integer postContVoto;
    private Integer postContComentario;
    private String postDuracion;
    private Boolean postEstado;
    private boolean upVote;
    private boolean downVote;
}
