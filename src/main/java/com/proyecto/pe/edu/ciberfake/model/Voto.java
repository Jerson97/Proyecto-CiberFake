package com.proyecto.pe.edu.ciberfake.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Voto {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "VOT_ID")
    private Long votId;

    @Column(name = "VOT_TIPO")
    private VotoTipo votType;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "VOT_POST_ID", referencedColumnName = "POST_ID")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "VOT_USU_ID", referencedColumnName = "USU_ID")
    private Usuario usuario;
}
