package com.proyecto.pe.edu.ciberfake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class TokenVerificacion {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TOKV_ID")
    private Long tokvId;

    @Column(name = "TOKV_TOKEN")
    private String tokvToken;

    @OneToOne(fetch = LAZY)
    private Usuario usuario;

    @Column(name = "TOKV_FEC_EXP")
    private Instant tokvFecExp;
}
