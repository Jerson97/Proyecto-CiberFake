package com.proyecto.pe.edu.ciberfake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequest {
    private Long id;
    private String email;
    private String usuario;
    private String password;
    private String nombres;
    private String apellidos;
    private String codigo;
    private Boolean estado;
}
