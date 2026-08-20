package org.example.model;
import java.time.LocalDateTime;

import java.io.*;

public class InformacionParqueadero {
    private static int vehiculosEnIngreso;
    private static int vehiculosEnSalida;

    private static final String ARCHIVO = "InformacionParqueadero.txt";

    public static void registrarInformacion(int vehiculosEnIngreso, int vehiculosEnSalida) {

        String mensajeIngreso = "Total de vehiculos en ingreso: " + vehiculosEnIngreso;
        String mensajeSalida = "Total de vehiculos en salida: " + vehiculosEnSalida;

        String fechaRegistro = "Fecha de registro: " + LocalDateTime.now(); // Fecha actual en formato string

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(mensajeIngreso);
            writer.newLine();

            writer.write(mensajeSalida);
            writer.newLine();

            writer.write(fechaRegistro);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error al escribir la informacion del parqueadero: " + e.getMessage());
        }
    }

    public static void mostrarInformacion() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("La informacion del parqueadero esta vacia.");
            return;
        }

        System.out.println("INFORMACION DEL PARQUEADERO");

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                System.out.print(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer la informacion del parqueadero: " + e.getMessage());
        }
    }
}
