package org.example.model;

import java.io.*;

public class Bitacora {

    private static final String ARCHIVO = "bitacora.txt";

    public static void registrarMovimiento(String accion, String placa, int espacio, String resultado) {
   
        String mensaje = accion + " - " + placa + " - " + " Espacio: "+ espacio + " - " + resultado;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(mensaje);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir la bitácora: " + e.getMessage());
        }
    }

    public static void mostrarBitacora() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("La bitacora esta vacia.");
            return;
        }

        System.out.println("\nBITACORA");

        try(BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer la bitacora: " + e.getMessage());
        }
    }
}

