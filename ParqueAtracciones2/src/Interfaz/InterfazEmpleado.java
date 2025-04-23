package Interfaz;

import java.util.Scanner;

public class InterfazEmpleado {
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("=== MENÚ EMPLEADO ===");
            System.out.println("1. Validar ingreso a atraccion");
            System.out.println("2. Marcar asistencia de cliente");
            System.out.println("3. Ver historial de cliente");
            System.out.println("0. Salir");
            System.out.print("Elija una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) validarIngreso();
            else if (opcion == 2) marcarAsistencia();
            else if (opcion == 3) verHistorial();
            else if (opcion == 0) break;
            else System.out.println("Opcion invalida.");
        }
    }

    private void validarIngreso() {
        System.out.println("Validando ingreso...");
    }

    private void marcarAsistencia() {
        System.out.println("Marcando asistencia...");
    }

    private void verHistorial() {
        System.out.println("Mostrando historial...");
    }
}