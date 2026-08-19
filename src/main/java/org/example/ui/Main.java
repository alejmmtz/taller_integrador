package org.example.ui;

import org.example.model.ParqueaderoController;
import org.example.model.Bitacora;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.example.exceptions.EspacioOcupadoException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParqueaderoController controller = new ParqueaderoController();
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- SISTEMA DE PARQUEADERO ---");
            System.out.println("1. Registrar Vehículo");
            System.out.println("2. Dar Salida a Vehículo");
            System.out.println("3. Listar Vehículos Parqueados");
            System.out.println("4. Ver Bitácora de Movimientos");
            System.out.println("5. Guardar y Salir \n");
            System.out.print("Seleccione una opción \n");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("Registro de datos del Vehiculo \n");

                        System.out.print("Placa del vehiculo: ");
                        String placa = scanner.nextLine();

                        System.out.print("Tipo de vehiculo(MOTO/CARRO): ");
                        String tipo = scanner.nextLine();

                        int espacio = -1;
                        boolean espacioValido = false;

                        while (!espacioValido) {
                            try {
                                System.out.print("Espacio en el parqueadero (1-" + controller.getEspacios() + "): ");
                                espacio = scanner.nextInt();
                                scanner.nextLine();
                                espacioValido = true;
                            } catch (InputMismatchException e) {
                                System.out.println("Debes ingresar un número entero válido.");
                                scanner.nextLine();
                            }
                        }

                        try {
                            System.out.println(controller.registrarVehiculo(placa, tipo, espacio));
                        } catch (IllegalArgumentException | EspacioOcupadoException e) {
                            System.out.println("ERROR -> ( " + e.getMessage() + " )");
                        }

                        break;

                    case 2:
                        System.out.println("Ingresa Placa del Vehiculo a dar Salida \n");

                        System.out.print("Placa del vehiculo: ");
                        String placaSalida = scanner.nextLine();

                        System.out.println(controller.salidaVehiculo(placaSalida));

                        break;

                    case 3:
                        System.out.println(controller.mostrarVehiculos());
                        break;

                    case 4:
                        Bitacora.mostrarBitacora();
                        break;

                    case 5:
                        salir = true;
                        controller.guardarEstado();
                        System.out.println("Sesión cerrada.");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Caracter inválido. Tiene que ingresar un número.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}
