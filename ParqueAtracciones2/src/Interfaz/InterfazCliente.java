package Interfaz;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import persistencia.ArchivoPlano;
import persona.*;
import tiquetes.*;
import tiquetes.Cliente;

public class InterfazCliente {

    private Scanner scanner = new Scanner(System.in);    
    private ArchivoPlano archivoPlano = new ArchivoPlano();
    private List<Cliente> clientes;
    private List<Empleado> empleados; // Nueva lista para empleados

    public InterfazCliente(List<Cliente> clientes) {
        this.clientes = clientes;
        this.empleados = cargarEmpleadosDesdeCSV(); // Cargamos empleados al iniciar
    }

    // Método para cargar empleados desde CSV
    private List<Empleado> cargarEmpleadosDesdeCSV() {
        List<Empleado> empleados = new ArrayList<>();
        List<String> lineasEmpleados = archivoPlano.leer("datos/empleados.csv");
        
        for (String linea : lineasEmpleados) {
            String[] datos = linea.split(",");
            String tipo = datos[0];
            String login = datos[1];
            String password = datos[2];
            String nombre = datos[3];
            int id = Integer.parseInt(datos[4]);
            String departamento = datos[5];
            
            // Crear LocalDateTime para el turno (usamos fecha actual como base)
            LocalDateTime fechaBase = LocalDateTime.now();
            Turno turno;
            
            if (datos[6].equals("Diurno")) {
                turno = new Turno("Diurno", 
                    fechaBase.withHour(8).withMinute(0),  // 8:00 AM
                    fechaBase.withHour(16).withMinute(0)); // 4:00 PM
            } else {
                turno = new Turno("Nocturno", 
                    fechaBase.withHour(16).withMinute(0),   // 4:00 PM
                    fechaBase.withHour(24).withMinute(0));  // 12:00 AM
            }

            switch (tipo) {
                case "Cajero":
                    Cajero cajero = new Cajero(login, password, nombre, id, departamento, turno, true);
                    empleados.add(cajero);
                    break;
                // ... otros casos de empleados
            }
        }
        return empleados;
    }

    public void iniciar() {
        while (true) {
            System.out.println("=== MENU CLIENTE ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Comprar tiquete");
            System.out.println("3. Consultar tiquetes");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> comprarTiquete();
                case 3 -> consultarTiquetes();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void registrarCliente() {
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su Login: ");
        String login = scanner.nextLine();
        System.out.print("Ingrese su Contraseña: ");
        String password = scanner.nextLine();

        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Ese cliente ya esta registrado.");
                return;
            }
        }

        Cliente nuevo = new Cliente(login, password, nombre, new ArrayList<>());
        clientes.add(nuevo);
        System.out.println("Cliente registrado exitosamente.");
    }


    private void comprarTiquete() {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        Cliente cliente = buscarCliente(nombre);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("¿Desea FastPass? (s/n): ");
        boolean fastPass = scanner.nextLine().equalsIgnoreCase("s");

        System.out.println("Seleccione el tipo de compra:");
        System.out.println("1. Compra Virtual");
        System.out.println("2. Compra en Taquilla");
        int tipoCompra = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Seleccione el tipo de tiquete:");
        System.out.println("1. Básico\n2. Familiar\n3. Oro\n4. Diamante\n5. Entrada Individual");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        Tiquete nuevo = switch (tipo) {
            case 1 -> new TiqueteBasico(fastPass);
            case 2 -> new TiqueteFamiliar(new ArrayList<>(), fastPass);
            case 3 -> new TiqueteOro(new ArrayList<>(), fastPass);
            case 4 -> new TiqueteDiamante(new ArrayList<>(), fastPass);
            case 5 -> {
                System.out.print("Ingrese el nombre de la atracción: ");
                String nombreAtraccion = scanner.nextLine();
                yield new EntradaIndividual(nombreAtraccion, fastPass);
            }
            default -> null;
        };

        if (nuevo == null) {
            System.out.println("Tipo de tiquete inválido.");
            return;
        }

        if (tipoCompra == 1) {
            // Venta online
            System.out.print("Método de pago (ej. tarjeta, PayPal): ");
            String metodoPago = scanner.nextLine();
            VentaOnline ventaOnline = new VentaOnline(cliente, metodoPago, new Date(), 0);
            if (ventaOnline.procesarPago()) {
                cliente.comprarTiquete(nuevo);
                System.out.println(ventaOnline.generarFactura());
                ventaOnline.enviarConfirmacion();
            } else {
                System.out.println("Error en el procesamiento de pago.");
            }
        } else if (tipoCompra == 2) {
            // Compra en taquilla
            System.out.println("Cajeros disponibles:");
            for (Empleado emp : empleados) {
                if (emp instanceof Cajero) {
                    System.out.println("- ID: " + emp.getId() + " | Nombre: " + emp.getNombre());
                }
            }
            
            System.out.print("Ingrese el ID del cajero: ");
            int idCajero = scanner.nextInt();
            scanner.nextLine();
            
            Cajero cajero = null;
            for (Empleado empleado : empleados) {
                if (empleado instanceof Cajero && empleado.getId() == idCajero) {
                    cajero = (Cajero) empleado;
                    break;
                }
            }
            
            if (cajero == null) {
                System.out.println("Cajero no válido.");
                return;
            }
            
            Taquilla taquilla = new Taquilla(new ArrayList<>());
            taquilla.asignarCajero(cajero);
            taquilla.registrarVenta(nuevo.getTipo(), 0);  
            cliente.comprarTiquete(nuevo);
            System.out.println("Compra realizada en taquilla con el cajero: " + cajero.getNombre());
        } else {
            System.out.println("Opción inválida para tipo de compra.");
        }
    }
    private void consultarTiquetes() {
        System.out.print("Nombre del cliente: ");
        String nombre = scanner.nextLine();
        Cliente cliente = buscarCliente(nombre);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        List<Tiquete> tiquetes = cliente.getTiquetes();
        if (tiquetes.isEmpty()) {
            System.out.println("No tiene tiquetes.");
        } else {
            for (Tiquete t : tiquetes) {
                System.out.println("- " + t.getTipo() + " | FastPass: " + t.hasFastPass() + " | Usado: " + t.isUsado());
            }
        }
    }

    private Cliente buscarCliente(String nombre) {
        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }
}
