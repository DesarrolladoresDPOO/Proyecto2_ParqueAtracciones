package Interfaz;

import java.util.Scanner;

public class InterfazAdmin {
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Crear atracción");
            System.out.println("2. Modificar atracción");
            System.out.println("3. Eliminar atracción");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) crearAtraccion();
            else if (opcion == 2) modificarAtraccion();
            else if (opcion == 3) eliminarAtraccion();
            else if (opcion == 0) break;
            else System.out.println("Opción inválida.");
        }
    }

    private void crearAtraccion() {
        System.out.println("Creando atracción...");
    }

    private void modificarAtraccion() {
        System.out.println("Modificando atracción...");
    }

    private void eliminarAtraccion() {
        System.out.println("Eliminando atracción...");
    }
}