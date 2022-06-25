package com.proyecto.pe.edu.ciberfake.model;

import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;

import java.util.Arrays;

public enum VotoTipo {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    private int direccion;

    VotoTipo(int direccion) {
    }

    public static VotoTipo lookup(Integer direccion) {
        return Arrays.stream(VotoTipo.values())
                .filter(value -> value.getDireccion().equals(direccion))
                .findAny()
                .orElseThrow(() -> new CiberFakeException("Voto no encontrado"));
    }

    public Integer getDireccion() {
        return direccion;
    }
}
