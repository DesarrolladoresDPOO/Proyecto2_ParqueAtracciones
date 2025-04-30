package Interfaz;

import java.util.*;

import atracciones.Atraccion;
import tiquetes.Cliente;
import tiquetes.Tiquete;
import tiquetes.TiqueteBasico;
import tiquetes.TiqueteDiamante;
import tiquetes.TiqueteFamiliar;
import tiquetes.TiqueteOro;

import java.io.*;

public class InterfazCliente {
    private List<Cliente> clientes;

    public InterfazCliente() {
        this.clientes = leerClientesDesdeCSV("datos/clientes.csv");
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== MENU CLIENTE ===");
            System.out.println("1. Comprar tiquete");
            System.out.println("2. Consultar tiquetes de un cliente");
            System.out.println("3. Registrar un nuevo cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    comprarTiquete();
                    break;
                case "2":
                    consultarTiquetesCliente();
                    break;
                case "3":
                	registrarseComoCliente();
                    break;
                case "0":
                    escribirClientesEnCSV("datos/clientes.csv");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
    
    /**
	 * REGISTRO DE UN NUEVO CLIENTE
	 */
    public void registrarseComoCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del nuevo cliente:");
        String nombreCliente = scanner.nextLine();

        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombreCliente)) {
                System.out.println("Ya existe un cliente con ese nombre.");
                return;
            }
        }

        System.out.println("Ingrese login:");
        String login = scanner.nextLine();

        System.out.println("Ingrese contraseña:");
        String password = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(login, password, nombreCliente, new ArrayList<>());
        clientes.add(nuevoCliente);
        escribirClientesEnCSV("datos/clientes.csv");

        System.out.println("Cliente registrado exitosamente sin comprar un tiquete.");
    }
    
    /**
	 * COMPRAR UN TIQUETE PARA UN CLIENTE, SI ESTE NO EXISTE SE REGISTRA
	 */
    private void comprarTiquete() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Tiquete> tiqueteCliente = new ArrayList<>();
        System.out.println("Ingrese el nombre del cliente que desea comprar un tiquete:");
        String nombreCliente = scanner.nextLine();

        Cliente clienteEncontrado = null;
        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombreCliente)) {
                clienteEncontrado = c;
                break;
            }
        }

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado. ¿Desea registrarlo? (s/n)");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.println("Ingrese login:");
                String login = scanner.nextLine();
                System.out.println("Ingrese contraseña:");
                String password = scanner.nextLine();
                clienteEncontrado = new Cliente(login, password, nombreCliente, tiqueteCliente);
                clientes.add(clienteEncontrado);
                System.out.println("Cliente registrado exitosamente.");
            } else {
                System.out.println("Venta cancelada.");
                return;
            }
        }

        System.out.println("¿Desea agregar FastPass? (s/n)");
        boolean fastPass = scanner.nextLine().equalsIgnoreCase("s");

        System.out.println("Seleccione el tipo de tiquete a comprar:");
        System.out.println("1. Básico");
        System.out.println("2. Familiar");
        System.out.println("3. Oro");
        System.out.println("4. Diamante");
        int tipo = Integer.parseInt(scanner.nextLine());

        List<Atraccion> atraccionesVacias = new ArrayList<>(); // Simulación
        Tiquete nuevo;
        if (tipo == 1) {
            nuevo = new TiqueteBasico(fastPass);
        } else if (tipo == 2) {
            nuevo = new TiqueteFamiliar(atraccionesVacias, fastPass);
        } else if (tipo == 3) {
            nuevo = new TiqueteOro(atraccionesVacias, fastPass);
        } else if (tipo == 4) {
            nuevo = new TiqueteDiamante(atraccionesVacias, fastPass);
        } else {
            System.out.println("Tipo de tiquete inválido.");
            return;
        }

        clienteEncontrado.getTiquetes().add(nuevo);
        System.out.println("Tiquete agregado exitosamente al cliente " + clienteEncontrado.getNombre());

        escribirClientesEnCSV("datos/clientes.csv");
    }

    /**
	 * CONSULTA LOS TIQUETES DE UN CLIENTE DADO
	 */
    private void consultarTiquetesCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del cliente:");
        String nombreCliente = scanner.nextLine();
        boolean encontrado = false;

        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombreCliente)) {
                encontrado = true;
                List<Tiquete> listaTiq = c.getTiquetes();
                if (listaTiq.isEmpty()) {
                    System.out.println("Este cliente no tiene tiquetes.");
                } else {
                    System.out.println("Tiquetes de " + c.getNombre() + ":");
                    for (Tiquete t : listaTiq) {
                        System.out.println("- Tipo: " + t.getTipo() + ", Usado: " + (t.isUsado() ? "Sí" : "No"));
                    }
                }
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Cliente no encontrado.");
        }
    }

    /**
	 * METODOS LECTURA Y ESCRITURA CLIENTES
	 */
    private List<Cliente> leerClientesDesdeCSV(String ruta) {
        List<Cliente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    String login = partes[0];
                    String password = partes[1];
                    String nombre = partes[2];
                    String[] tiposTiquetes = partes[3].split(";");
                    ArrayList<Tiquete> tiquetes = new ArrayList<>();
                    for (String tipo : tiposTiquetes) {
                        if (tipo.equalsIgnoreCase("Basico")) tiquetes.add(new TiqueteBasico(false));
                        else if (tipo.equalsIgnoreCase("Familiar")) tiquetes.add(new TiqueteFamiliar(new ArrayList<>(), false));
                        else if (tipo.equalsIgnoreCase("Oro")) tiquetes.add(new TiqueteOro(new ArrayList<>(), false));
                        else if (tipo.equalsIgnoreCase("Diamante")) tiquetes.add(new TiqueteDiamante(new ArrayList<>(), false));
                    }
                    lista.add(new Cliente(login, password, nombre, tiquetes));
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo CSV: " + e.getMessage());
        }
        return lista;
    }

    private void escribirClientesEnCSV(String ruta) {
        ArrayList<String> lineasCliente = new ArrayList<>();
        for (Cliente cliente : clientes) {
            StringBuilder sb = new StringBuilder();
            sb.append(cliente.getLogin()).append(",");
            sb.append(cliente.getPassword()).append(",");
            sb.append(cliente.getNombre()).append(",");

            ArrayList<String> nombresTiquetes = new ArrayList<>();
            for (Tiquete t : cliente.getTiquetes()) {
                nombresTiquetes.add(t.getClass().getSimpleName().replace("Tiquete", ""));
            }
            sb.append(String.join(";", nombresTiquetes));

            lineasCliente.add(sb.toString());
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for (String linea : lineasCliente) {
                pw.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }
}