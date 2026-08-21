package org.example.model;

import java.util.ArrayList;

import org.example.exceptions.EspacioOcupadoException;
import org.example.exceptions.VehiculoNoEncontradoException;

public class ParqueaderoController {
    private Vehiculo[] vehiculos;
    private static final String ARCHIVO_DATOS = "parqueadero.dat";
    private static final int ESPACIO_MAX = 20;
    private int espacios;
    private static int vehiculosEnIngreso;
    private static int vehiculosEnSalida;

    public ParqueaderoController() {
        this.vehiculos = new Vehiculo[ESPACIO_MAX];
        this.espacios = ESPACIO_MAX;
        cargarDatos();
        cargarEstadisticas();
    }

    public int getEspacios() { return espacios; }

    public static int getVehiculosEnIngreso() { return vehiculosEnIngreso; }

    public static int getVehiculosEnSalida() { return vehiculosEnSalida; }

    private void cargarDatos() {
        try {
            ArrayList<Vehiculo> listaCargada = ManejoArchivo.cargarEstado(ARCHIVO_DATOS);
            for (Vehiculo v : listaCargada) {
                if (v != null) {
                    int index = v.getNumeroParqueadero() - 1;
                    if (index >= 0 && index < vehiculos.length) {
                        vehiculos[index] = v;
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Error al cargar datos: " +  e.getMessage());
        }
    }

    public void cargarEstadisticas() {
        try {
            vehiculosEnIngreso = InformacionParqueadero.cargarTotalIngresos();
            vehiculosEnSalida = InformacionParqueadero.cargarTotalSalidas();
        } catch (Exception e) {
            System.out.println("Error al cargar datos: " +  e.getMessage());
            vehiculosEnIngreso = 0;
            vehiculosEnSalida = 0;
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
        vehiculosEnIngreso++;

        return "Vehiculo exitosamente registrado: \n" + vehiculos[index].toString();
    }

    public String salidaVehiculo(String placa) throws VehiculoNoEncontradoException {
        for (int i = 0; i < vehiculos.length; i++) {
            if (vehiculos[i] != null && vehiculos[i].getPlaca().equals(placa)) {
                int numeroParqueadero = vehiculos[i].getNumeroParqueadero();
                vehiculos[i] = null;
                vehiculosEnSalida++;
                Bitacora.registrarMovimiento("SALIDA", placa, numeroParqueadero, "EXITOSO");
                return "Vehículo con placa " + placa + " salió del parqueadero en espacio: " + numeroParqueadero;
            }
        }
        throw new VehiculoNoEncontradoException("Vehículo con placa " + placa + " no encontrado.");
    }

    public String mostrarVehiculos() {
        String lista = "\nEstado actual del parqueadero:\n\n";
        int ocupados = 0;

        for (int i = 0; i < vehiculos.length; i++) {
            int numeroParqueadero = i + 1;
            Vehiculo vehiculo = vehiculos[i];

            if (vehiculo == null) {
                lista += "Espacio " + numeroParqueadero +" se encuentra vacío. \n";
            } else {
                lista += vehiculo.toString()+"\n";
                ocupados++;
            }
        }
        lista += "\n--- ESTADÍSTICAS ---\n";
        lista += "Espacios ocupados: " + ocupados + "/" + ESPACIO_MAX + "\n";
        lista += "Total ingresos: " + vehiculosEnIngreso + "\n";
        lista += "Total salidas: " + vehiculosEnSalida + "\n";

        return lista;
    }

    public void guardarEstado() {
        try {
            ArrayList<Vehiculo> listaParaGuardar = new ArrayList<>();
            for (Vehiculo v : vehiculos) {
                if (v != null) {
                    listaParaGuardar.add(v);
                }
            }
            ManejoArchivo.guardarEstado(ARCHIVO_DATOS, listaParaGuardar);
            InformacionParqueadero.registrarInformacion(vehiculosEnIngreso, vehiculosEnSalida);
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " +  e.getMessage());
        }
    }
}