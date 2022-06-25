package com.proyecto.pe.edu.ciberfake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefresh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKR_ID")
    private Long tokrId;

    @Column(name = "TOKR_TOKEN")
    private String tokrToken;

    @Column(name = "TOKR_FEC_CREA")
    private Instant tokrFecCrea;
}
