package org.example.model;

import java.io.*;
import java.util.ArrayList;

public class ManejoArchivo {

    public static void guardarEstado(String archivo, ArrayList<Vehiculo> lista) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(lista);
            System.out.println("Datos guardados en: " + archivo);
            
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Vehiculo> cargarEstado(String archivo) {
      File file = new File(archivo);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Vehiculo>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Clase no encontrada.");
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}
