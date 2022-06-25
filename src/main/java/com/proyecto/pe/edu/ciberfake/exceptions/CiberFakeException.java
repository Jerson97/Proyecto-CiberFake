package com.proyecto.pe.edu.ciberfake.exceptions;

public class CiberFakeException extends RuntimeException {
    public CiberFakeException(String exMessage, Exception exception){
        super(exMessage, exception);
    }

    public CiberFakeException(String exMessage){
        super(exMessage);
    }
}
