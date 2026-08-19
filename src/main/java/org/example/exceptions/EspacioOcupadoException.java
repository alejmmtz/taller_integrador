package org.example.exceptions;

public class EspacioOcupadoException extends Exception {

    public EspacioOcupadoException(String mensaje) {
        super(mensaje);
    }

    public EspacioOcupadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}