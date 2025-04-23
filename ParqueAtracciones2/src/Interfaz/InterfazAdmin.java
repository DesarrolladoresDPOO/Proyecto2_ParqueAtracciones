package Interfaz;

import java.util.ArrayList;
import java.util.Scanner;
import atracciones.AtraccionMecanica;
import atracciones.AtraccionCultural;
import persistencia.ArchivoPlano;

public class InterfazAdmin {
    private Scanner scanner = new Scanner(System.in);
    private AtraccionMecanica atraccionMecanica;
    private AtraccionCultural atraccionCultural;
    private ArchivoPlano archivoPlano;

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
    	ArrayList<AtraccionMecanica> atraccionesRegistradas = new ArrayList<>();
        System.out.println("Ingrese el tipo de atracción que desea registrar: ");
        System.out.println("1. Atracción Mecánica");
        System.out.println("2. Atracción Cultural");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        
        if (opcion == 1) {
            System.out.println("== Registro Atracción Mecánica ==");

            System.out.print("Ingrese el nombre de la atraccion: ");
            String nombre = scanner.nextLine();

            System.out.print("Cupo máximo: ");
            int cupoMaximo = scanner.nextInt();

            System.out.print("Cantidad de empleados encargados: ");
            int empleadosEncargados = scanner.nextInt();

            System.out.print("¿Está disponible con clima adverso? (true/false): ");
            boolean disponibleClima = scanner.nextBoolean();
            scanner.nextLine(); 

            System.out.print("Nivel de exclusividad (oro/plata/etc): ");
            String nivelExclusividad = scanner.nextLine();

            System.out.print("Altura mínima (cm): ");
            int minimoAltura = scanner.nextInt();

            System.out.print("Altura máxima (cm): ");
            int maximoAltura = scanner.nextInt();

            System.out.print("Peso mínimo (kg): ");
            int minimoPeso = scanner.nextInt();

            System.out.print("Peso máximo (kg): ");
            int maximoPeso = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Restricciones de salud (ej: vértigo, marcapasos): ");
            String restriccionesSalud = scanner.nextLine();

            System.out.print("Nivel de riesgo (bajo/medio/alto): ");
            String nivelRiesgo = scanner.nextLine();

            this.atraccionMecanica = new AtraccionMecanica(
                nombre, cupoMaximo, empleadosEncargados, disponibleClima,
                nivelExclusividad, minimoAltura, maximoAltura,
                minimoPeso, maximoPeso, restriccionesSalud, nivelRiesgo
            );
            atraccionesRegistradas.add(atraccionMecanica);
            System.out.println("Atracción mecánica creada con éxito: " + nombre);
            archivoPlano.escribir("datos/atracciones.csv", atraccionesRegistradas);

        } else if (opcion == 2) {
            System.out.println("⚠️ Registro de atracción cultural aún no implementado.");
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private void modificarAtraccion() {
        System.out.println("Modificando atraccion...");
    }

    private void eliminarAtraccion() {
        System.out.println("Eliminando atraccion...");
    }
}