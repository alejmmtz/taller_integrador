package org.example.model;

import java.io.Serializable;

public class Vehiculo implements Serializable{
    private static final long serialVersionUID = 1L; 

    private String placa;
    private String tipo; 
    private int numeroParqueadero;

    public Vehiculo(String placa, String tipo, int numeroParqueadero) {
        this.placa = placa;
        this.tipo = tipo;
        this.numeroParqueadero = numeroParqueadero;
    }

    public String getPlaca() { return placa; }

    public String getTipo() { return tipo; }
        
    public int getNumeroParqueadero() { return numeroParqueadero; }

    @Override
    public String toString() {
        return "Espacio " + numeroParqueadero + " se encuentra ocupado -> Placa: " + placa + " (" + tipo + ")";
    }
}
