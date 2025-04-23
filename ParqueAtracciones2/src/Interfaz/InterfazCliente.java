package Interfaz;

import java.util.Scanner;

public class InterfazCliente {
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("=== MENÚ CLIENTE ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Consultar atracciones");
            System.out.println("3. Acceder a una atracción");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) registrarCliente();
            else if (opcion == 2) consultarAtracciones();
            else if (opcion == 3) accederAtraccion();
            else if (opcion == 0) break;
            else System.out.println("Opción inválida.");
        }
    }

    private void registrarCliente() {
        // TODO: Implementar con clases reales del modelo
        System.out.println("Registrando nuevo cliente...");
    }

    private void consultarAtracciones() {
        // TODO: Mostrar lista de atracciones disponibles
        System.out.println("Mostrando atracciones...");
    }

    private void accederAtraccion() {
        // TODO: Validar acceso a una atracción según requisitos
        System.out.println("Intentando acceder a una atracción...");
    }
}