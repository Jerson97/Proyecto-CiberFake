package com.proyecto.pe.edu.ciberfake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.proyecto.pe.edu.ciberfake.model.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoDto {
    private VotoTipo votoTipo;
    private Long postId;
}
