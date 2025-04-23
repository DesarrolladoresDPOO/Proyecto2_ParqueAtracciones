package Interfaz;

import java.util.Scanner;

public class InterfazAdmin {
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Crear atraccion");
            System.out.println("2. Modificar atraccion");
            System.out.println("3. Eliminar atraccion");
            System.out.println("0. Salir");
            System.out.print("Elija una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) crearAtraccion();
            else if (opcion == 2) modificarAtraccion();
            else if (opcion == 3) eliminarAtraccion();
            else if (opcion == 0) break;
            else System.out.println("Opcion invalida.");
        }
    }

    private void crearAtraccion() {
        System.out.println("Creando atraccion...");
    }

    private void modificarAtraccion() {
        System.out.println("Modificando atraccion...");
    }

    private void eliminarAtraccion() {
        System.out.println("Eliminando atraccion...");
    }
}