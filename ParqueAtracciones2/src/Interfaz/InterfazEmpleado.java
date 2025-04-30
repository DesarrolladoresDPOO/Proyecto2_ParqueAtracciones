package Interfaz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import persistencia.ArchivoPlano;
import persona.Turno;
import tiquetes.Cliente;
import persona.Administrador;
import persona.Cajero;
import persona.Cocinero;
import persona.Empleado;
import persona.LugarServicio;
import persona.OperadorMecanico;
import persona.ServicioGeneral;

public class InterfazEmpleado {
    private Scanner scanner = new Scanner(System.in);

    
    public void autenticarEmpleado() {
        System.out.println("== AUTENTICACION DE EMPLEADO ==");

        System.out.print("Usuario (Jero123): ");
        String usuarioIngresado = scanner.nextLine();

        System.out.print("Contraseña (pass8): ");
        String contraseñaIngresada = scanner.nextLine();

        ArchivoPlano archivoPlano = new ArchivoPlano();
        ArrayList<String> empleados = archivoPlano.leer("datos/empleados.csv");

        boolean autenticado = false;
        String rolEmpleado = "";

        for (String linea : empleados) {
            String[] partes = linea.split(",");
            if (partes.length >= 3) {
                String rol = partes[0];
                String usuario = partes[1];
                String contraseña = partes[2];

                if (!rol.equalsIgnoreCase("Administrador") &&
                    usuario.equals(usuarioIngresado) &&
                    contraseña.equals(contraseñaIngresada)) {
                    autenticado = true;
                    rolEmpleado = rol;
                    break;
                }
            }
        }

        if (autenticado) {
            System.out.println("Autenticación exitosa como " + rolEmpleado + ".");
            iniciar();
        } else {
            System.out.println("Usuario o contraseña incorrectos. Acceso denegado.");
        }
    }

    public void iniciar() {
        while (true) {
            System.out.println("=== MENÚ EMPLEADO ===");
            System.out.println("1. Validar ingreso a lugar de trabajo");
            System.out.println("2. Marcar asistencia del cliente");
            System.out.println("3. Ver historial del cliente");
            System.out.println("4. Consultar turno Empleado");
            System.out.println("0. Salir");
            System.out.print("Elija una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) validarIngreso();
            else if (opcion == 2) marcarAsistencia();
            else if (opcion == 3) verHistorial();
            else if (opcion == 4) verTurnos();
            else if (opcion == 0) break;
            else System.out.println("Opcion invalida.");
        }
    }


	/**
	 * VALIDACION DEL INGRESO DE UN EMPLEADO DADO
	 */
    public Empleado validarIngreso() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        try {
            BufferedReader br = new BufferedReader(new FileReader("datos/empleados.csv"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length < 7) continue;

                String rol = partes[0];
                String loginArchivo = partes[1];
                String passwordArchivo = partes[2];

                if (usuario.equals(loginArchivo) && contraseña.equals(passwordArchivo)) {
                    String nombre = partes[3];
                    int id = Integer.parseInt(partes[4]);
                    String lugar = partes[5];
                    String infoTurno = partes[6];
                    String tipoTurno = infoTurno.split(" ")[0];
                    String horario = infoTurno.substring(infoTurno.indexOf("(") + 1, infoTurno.indexOf(")"));
                    String[] horas = horario.split(" - ");
                    LocalDateTime inicio = LocalDateTime.parse(horas[0]);
                    LocalDateTime fin = LocalDateTime.parse(horas[1]);

                    Turno turno = new Turno(tipoTurno, inicio, fin);
                    switch (rol.toLowerCase()) {
                        case "cajero":
                            return new Cajero(loginArchivo, passwordArchivo, nombre, id, lugar, turno, false);
                        case "cocinero":
                            return new Cocinero(loginArchivo, passwordArchivo, nombre, id, lugar, turno, false);
                        case "operadormecanico":
                            return new OperadorMecanico(loginArchivo, passwordArchivo, nombre, id, lugar, false, null, turno);
                        case "serviciogeneral":
                            return new ServicioGeneral(loginArchivo, passwordArchivo, nombre, id, lugar, turno);
                        default:
                            System.out.println("Rol de empleado no reconocido: " + rol);
                            return null;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de empleados.");
            e.printStackTrace();
        }

        System.out.println("Usuario o contraseña incorrectos.");
        return null;
    }
    private Cliente autenticarCliente() {
        System.out.print("Usuario del cliente: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        try {
            BufferedReader br = new BufferedReader(new FileReader("datos/clientes.csv"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String usuarioCsv = partes[0];
                    String passwordCsv = partes[1];
                    String nombre = partes[2];
                    if (usuario.equals(usuarioCsv) && contraseña.equals(passwordCsv)) {
                        return new Cliente(usuarioCsv, passwordCsv, nombre, new ArrayList<>());
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void marcarAsistencia() {
        Cliente cliente = autenticarCliente();
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        LocalDate hoy = LocalDate.now();
        LocalTime hora = LocalTime.now();
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

        String lineaNueva = cliente.getNombre() + "," + hoy.format(formatterFecha) + "," + hora.format(formatterHora);

        File archivo = new File("datos/asistencias_clientes.csv");
        boolean yaRegistrado = false;

        try {
            if (archivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split(",");
                    if (partes.length >= 2 && partes[0].equals(cliente.getNombre()) && partes[1].equals(hoy.format(formatterFecha))) {
                        yaRegistrado = true;
                        break;
                    }
                }
                br.close();
            }

            if (!yaRegistrado) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.write(lineaNueva);
                bw.newLine();
                bw.close();
                System.out.println("Asistencia del cliente registrada.");
            } else {
                System.out.println("️El cliente ya ha sido registrado hoy.");
            }

        } catch (IOException e) {
            System.out.println("Error al registrar la asistencia.");
            e.printStackTrace();
        }
    }

    public void verHistorial() {
        System.out.print("Usuario del cliente: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña del cliente: ");
        String contraseña = scanner.nextLine();

        try {
            BufferedReader br = new BufferedReader(new FileReader("datos/clientes.csv"));
            String linea;
            String nombreCliente = null;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3 && partes[0].equals(usuario) && partes[1].equals(contraseña)) {
                    nombreCliente = partes[2];
                    break;
                }
            }

            br.close();

            if (nombreCliente == null) {
                System.out.println("Cliente no encontrado.");
                return;
            }

            File archivo = new File("datos/asistencias_clientes.csv");
            if (!archivo.exists()) {
                System.out.println("No hay historial de asistencias aun.");
                return;
            }

            BufferedReader b2 = new BufferedReader(new FileReader(archivo));
            String l2;
            boolean tiene = false;

            System.out.println("Historial de asistencias de " + nombreCliente + ":");
            while ((l2 = b2.readLine()) != null) {
                String[] p2 = l2.split(",");
                if (p2.length >= 3 && p2[0].equals(nombreCliente)) {
                    System.out.println("Fecha: " + p2[1] + " | Hora: " + p2[2]);
                    tiene = true;
                }
            }
            b2.close();

            if (!tiene) {
                System.out.println("No se encontraron asistencias registradas.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer historial.");
            e.printStackTrace();
        }
    }
    
    private void verTurnos() {
    	ArchivoPlano archivoPlano = new ArchivoPlano();
        ArrayList<String> lineasEmpleados = archivoPlano.leer("datos/empleados.csv");

        System.out.println("Ingrese el nombre del empleado para verificar si tiene turnos:");
        String nombreEmpleado = scanner.nextLine().trim();

        boolean encontrado = false;

        for (String linea : lineasEmpleados) {
            // Formato esperado: tipo,login,password,nombre,id,departamento,turno
            String[] datos = linea.split(",", 7);
            if (datos.length < 7) continue;

            String nombre = datos[3].trim();
            String turnoStr = datos[6].trim();  
            // Ejemplo: "Diurno (2025-04-02T08:00 - 2025-04-02T16:00)"

            if (nombre.equalsIgnoreCase(nombreEmpleado)) {
                encontrado = true;

                if (turnoStr.equalsIgnoreCase("null") || turnoStr.isEmpty()) {
                    System.out.println("Este empleado no tiene turno asignado.");
                    return;
                }

                System.out.println("Ingrese la fecha para consultar turno (YYYY-MM-DD):");
                String fechaTexto = scanner.nextLine().trim();

                try {
                    LocalDate fechaBuscada = LocalDate.parse(fechaTexto);
                    if (turnoStr.contains(fechaBuscada.toString())) {
                        System.out.println("El empleado tiene un turno asignado ese día:");
                        System.out.println(turnoStr);
                    } else {
                        System.out.println("No tiene turno asignado para esa fecha.");
                    }
                } catch (Exception e) {
                    System.out.println("Fecha inválida. Use el formato YYYY-MM-DD.");
                }
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Empleado no encontrado.");
        }
    }
}