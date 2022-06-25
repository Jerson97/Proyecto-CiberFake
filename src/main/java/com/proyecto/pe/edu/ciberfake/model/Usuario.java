package com.proyecto.pe.edu.ciberfake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USU_ID")
    private Long usuId;

    @Column(name = "USU_NOMBRE", nullable = false, length = 50)
    @NotBlank(message = "El Nombre de Usuario es requerido")
    private String usuNombre;

    @Column(name = "USU_PASSWORD", nullable = false)
    @NotBlank(message = "El Password es requerido")
    private String usuPassword;

    @Column(name = "USU_EMAIL", nullable = false, length = 50, unique=true)
    @Email
    @NotEmpty(message = "El correo electronico es requerido")
    private String usuEmail;

    @Column(name = "USU_PER_NOMBRE", nullable = false, length = 50)
    @NotBlank(message = "El Nombre de la Persona es requerido")
    private String usuPerNombre;

    @Column(name = "USU_PER_APELLIDO", nullable = false, length = 50)
    @NotBlank(message = "El Apellido de la Persona es requerido")
    private String usuPerApellido;

    @Column(name = "USU_PER_CODIGO", nullable = false, length = 10, unique=true)
    @NotBlank(message = "El Codigo del alumno es requerido")
    private String usuPerCodigo;

    @Column(name = "USU_FEC_CREA", updatable = false)
    private Instant usuFecCrea;

    @Column(name = "USU_FEC_ACTUALIZA")
    private Instant usuFecActualiza;

    @Column(name = "USU_ESTADO")
    private boolean usuEstado;

    @PrePersist
    void creationDate(){
        usuFecCrea = java.time.Instant.now();
    }

    @PreUpdate
    void updateDate(){
        usuFecActualiza = java.time.Instant.now();
    }
}
