package org.example.model;

import java.util.ArrayList;

import org.example.exceptions.EspacioOcupadoException;

public class ParqueaderoController {
    private Vehiculo[] vehiculos;
    private static final String ARCHIVO_DATOS = "parqueadero.dat";
    private static final int ESPACIO_MAX = 20;
    private int espacios;

    public ParqueaderoController() {
        this.vehiculos = new Vehiculo[ESPACIO_MAX];
        this.espacios = ESPACIO_MAX;
        cargarDatos();
    }

    public int getEspacios() { return espacios; }


    private void cargarDatos() {
        ArrayList<Vehiculo> listaCargada = ManejoArchivo.cargarEstado(ARCHIVO_DATOS);
        for (Vehiculo v : listaCargada) {
            if (v != null) {
                int index = v.getNumeroParqueadero() - 1;
                if (index >= 0 && index < vehiculos.length) {
                    vehiculos[index] = v;
                }
            }   
      
        }
    }

    public String registrarVehiculo(String placa, String tipo, int numeroParqueadero) throws EspacioOcupadoException {
        int index = numeroParqueadero - 1; 

        if (index < 0 || index >= vehiculos.length) {
            throw new IllegalArgumentException("Número de parqueadero inválido. (1-"+ espacios +")");
        }

        if (vehiculos[index] != null) {
            Bitacora.registrarMovimiento("INGRESO", placa, numeroParqueadero, "(ESPACIO OCUPADO)");
            throw new EspacioOcupadoException("El espacio " + numeroParqueadero + " del parqueadero se encuentra ocupado.");
        }

        Vehiculo nuevoVehiculo = new Vehiculo(placa, tipo, numeroParqueadero);
        vehiculos[index] = nuevoVehiculo;

        Bitacora.registrarMovimiento("INGRESO", placa, numeroParqueadero, "EXITOSO");

        return "Vehiculo exitosamente registrado: \n"+vehiculos[index].toString();

    }

    public String salidaVehiculo(String placa) {
        String resultado = "Vehículo no encontrado.";

        for (int i = 0; i < vehiculos.length; i++) {

            if (vehiculos[i] != null && vehiculos[i].getPlaca().equals(placa)) {
                int numeroParqueadero = vehiculos[i].getNumeroParqueadero();
                resultado = "Vehículo con placa " + placa + " salió del parqueadero en espacio: "+ numeroParqueadero;
                vehiculos[i] = null;
                break;

            }
        }

        return resultado;
    }


    public String mostrarVehiculos() {
        String lista = "\nEstado actual del parqueadero:\n\n";

        for (int i = 0; i < vehiculos.length; i++) {
            int numeroParqueadero = i + 1; 
            Vehiculo vehiculo = vehiculos[i];

            if (vehiculo == null) {

            lista += "Espacio " + numeroParqueadero +" se encuentra vacío. \n";
            } else {
               lista += vehiculo.toString()+"\n";
            }
        }

        return lista;
    }

    public void guardarEstado() {
        ArrayList<Vehiculo> listaParaGuardar = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v != null) {
                listaParaGuardar.add(v);
            }
        }
        ManejoArchivo.guardarEstado(ARCHIVO_DATOS, listaParaGuardar);
    }
}
