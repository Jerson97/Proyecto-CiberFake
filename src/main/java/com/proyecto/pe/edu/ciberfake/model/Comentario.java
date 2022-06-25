package com.proyecto.pe.edu.ciberfake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "COME_ID")
    private Long comeId;

    @Column(name = "COME_TEXTO", nullable = false)
    @NotEmpty
    private String comeTexto;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COME_POST_ID", referencedColumnName = "POST_ID")
    private Post post;

    @Column(name = "COME_FEC_CREA", updatable = false)
    private Instant comeFecCrea;

    @Column(name = "COME_FEC_ACTUALIZA")
    private Instant comeFecActualiza;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COME_USU_ID", referencedColumnName = "USU_ID")
    private Usuario usuario;

    @Column(name = "COME_ESTADO")
    private boolean comeEstado;

    @PrePersist
    void creationDate(){
        comeFecCrea = java.time.Instant.now();
    }

    @PreUpdate
    void updateDate(){
        comeFecActualiza = java.time.Instant.now();
    }
}
