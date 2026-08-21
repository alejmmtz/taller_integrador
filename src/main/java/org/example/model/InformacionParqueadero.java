package org.example.model;

import java.time.LocalDateTime;
import java.io.*;

public class InformacionParqueadero {
    private static final String ARCHIVO = "InformacionParqueadero.txt";
    private static final String ARCHIVO_ESTADISTICAS = "estadisticas.dat";

    public static void registrarInformacion(int vehiculosEnIngreso, int vehiculosEnSalida) {
        String fechaRegistro = "Fecha de registro: " + LocalDateTime.now();

        String mensaje = "\n" +
                fechaRegistro + "\n" +
                "Total de vehiculos en ingreso: " + vehiculosEnIngreso + "\n" +
                "Total de vehiculos en salida: " + vehiculosEnSalida + "\n" +
                "Vehiculos en parqueadero: " + (vehiculosEnIngreso - vehiculosEnSalida) + "\n" +
                "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(mensaje);
        } catch (IOException e) {
            System.out.println("Error al escribir la información del parqueadero: " + e.getMessage());
        }

        guardarEstadisticas(vehiculosEnIngreso, vehiculosEnSalida);
    }

    private static void guardarEstadisticas(int ingresos, int salidas) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_ESTADISTICAS))) {
            dos.writeInt(ingresos);
            dos.writeInt(salidas);
        } catch (IOException e) {
            System.out.println("Error al guardar estadísticas: " + e.getMessage());
        }
    }

    public static int cargarTotalIngresos() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ARCHIVO_ESTADISTICAS))) {
            return dis.readInt();
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            System.out.println("Error al cargar total ingresos: " + e.getMessage());
            return 0;
        }
    }

    public static int cargarTotalSalidas() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ARCHIVO_ESTADISTICAS))) {
            dis.readInt();
            return dis.readInt();
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            System.out.println("Error al cargar total salidas: " + e.getMessage());
            return 0;
        }
    }

    public static void mostrarInformacion() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("La información del parqueadero está vacía.");
            return;
        }

        System.out.println("\nINFORMACION DEL PARQUEADERO\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer la información del parqueadero: " + e.getMessage());
        }
    }
}