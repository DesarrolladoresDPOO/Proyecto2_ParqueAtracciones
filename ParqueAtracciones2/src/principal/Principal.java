package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import atracciones.Atraccion;
import atracciones.AtraccionCultural;
import atracciones.AtraccionMecanica;
import atracciones.Espectaculo;
import atracciones.Temporada;

import persona.Administrador;
import persona.Cajero;
import persona.Cocinero;
import persona.Empleado;
import persona.LugarServicio;
import persona.LugarTrabajo;
import persona.OperadorMecanico;
import persona.ServicioGeneral;
import persona.Turno;
import persona.Usuario;

import persistencia.ArchivoPlano;

import tiquetes.Cliente;
import tiquetes.EntradaIndividual;
import tiquetes.Taquilla;
import tiquetes.Tiquete;
import tiquetes.TiqueteOro;
import tiquetes.TiqueteTemporada;
import tiquetes.VentaOnline;
import tiquetes.TiqueteBasico;
import tiquetes.TiqueteFamiliar;
import tiquetes.TiqueteDiamante;

import java.util.ArrayList;
import java.util.List;

public class Principal {
    private BufferedReader br;
    
    // Instancias de objetos del dominio
    private AtraccionCultural atraccionCultural;
    private AtraccionMecanica atraccionMecanica;
    private Espectaculo espectaculo;
    private Temporada temporada;
    private Administrador administrador;
    private Cajero cajero;
    private Cocinero cocinero;
    private LugarServicio lugarServicio;
    private OperadorMecanico operadorMecanico;
    private ServicioGeneral servicioGeneral;
    private Turno turnoDiurno;
    private Turno turnoNocturno;
    private Cliente cliente;
    private Cliente cliente2;
    private EntradaIndividual entradaIndividual;
    private Taquilla taquilla;
    private Tiquete tiquete;
    private TiqueteBasico tiqueteBasico;
    private TiqueteDiamante tiqueteDiamante;
    private TiqueteFamiliar tiqueteFamiliar;
    private TiqueteOro tiqueteOro;
    private TiqueteTemporada tiqueteTemporada;
    private VentaOnline ventaOnline;

    // Colecciones generales
    private List<Empleado> empleados;
    private List<Cliente> clientes;
    private List<Atraccion> atraccionesDiamante;
    private List<Atraccion> atraccionesFamiliares;
    private List<Atraccion> atraccionesOro;
    
    // Objeto para lectura de archivos
    private ArchivoPlano archivoPlano;

    public Principal() {
    	
        // Inciamos objetos
        this.archivoPlano = new ArchivoPlano();
        this.empleados = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.atraccionesDiamante = new ArrayList<>();
        this.atraccionesFamiliares = new ArrayList<>();
        
        /**
        ATRACCIONES FIJAS
        */
        this.atraccionCultural = new AtraccionCultural("Carrusel", 20, 2, true, "familiar", 5);
        this.atraccionMecanica = new AtraccionMecanica("MontanaRusa", 10, 4, true, "oro", 150, 190, 40, 90, "vertigo", "medio");
        LocalDateTime inicio1 = LocalDateTime.of(2025, 1, 10, 12, 0);
        LocalDateTime fin1 = LocalDateTime.of(2025, 3, 1, 12, 0);
        this.temporada = new Temporada(inicio1, fin1);
        this.espectaculo = new Espectaculo("DesfileInvierno", temporada, "18:00 - 21:00", false);
        
        /**
         TURNOS FIJOS
         */
        // Turno diurno: 2 de abril de 2025, de 8:00 AM a 4:00 PM
		LocalDateTime inicioDiurno = LocalDateTime.of(2025, 4, 2, 8, 0);
		LocalDateTime finDiurno = LocalDateTime.of(2025, 4, 2, 16, 0);
		turnoDiurno = new Turno("Diurno", inicioDiurno, finDiurno);

		// Turno nocturno: 2 de abril de 2025, de 6:00 PM a 3 de abril de 2025, 2:00 AM
		LocalDateTime inicioNocturno = LocalDateTime.of(2025, 4, 2, 18, 0);
		LocalDateTime finNocturno = LocalDateTime.of(2025, 4, 3, 2, 0);
		turnoNocturno = new Turno("Nocturno", inicioNocturno, finNocturno);

		// Fecha en que se asignaran los turnos
		LocalDate fechaTurno = LocalDate.of(2025, 4, 2); // 2 de abril de 2025
        
		/**
        LECTURA EMPLEADOS DESDE CSV
        */
        ArrayList<String> lineasEmpleados = archivoPlano.leer("datos/empleados.csv");
        for (String linea : lineasEmpleados) {
            // Formato: tipo,login,password,nombre,id,departamento,turnoNombre
            String[] datos = linea.split(",");
            String tipo = datos[0];
            String login = datos[1];
            String password = datos[2];
            String nombre = datos[3];
            int id = Integer.parseInt(datos[4]);
            String departamento = datos[5];
            
            switch (tipo) {
                case "Administrador":
                	administrador = new Administrador(login, password, nombre, id, departamento, turnoDiurno);
                    administrador.asignarTurno(fechaTurno, turnoDiurno);
                    this.empleados.add(administrador);
                    break;
                case "Cajero":
                	cajero= new Cajero(login, password, nombre, id, departamento, turnoDiurno, true);
                	cajero.asignarTurno(fechaTurno, turnoDiurno);
                	this.empleados.add(cajero);
                    break;
                case "Cocinero":
                	cocinero= new Cocinero(login, password, nombre, id, departamento, turnoDiurno, true);
                    cocinero.asignarTurno(fechaTurno, turnoDiurno);
                    this.empleados.add(cocinero);
                    break;
                case "OperadorMecanico":
                	operadorMecanico= new OperadorMecanico(login, password, nombre, id, departamento, true, atraccionMecanica, turnoDiurno);
                    operadorMecanico.asignarTurno(fechaTurno, turnoDiurno);
                    this.empleados.add(operadorMecanico);
                    break;
                case "ServicioGeneral":
                	servicioGeneral= new ServicioGeneral(login, password, nombre, id, departamento, turnoDiurno);
                    servicioGeneral.asignarTurno(fechaTurno, turnoNocturno);
                    this.empleados.add(servicioGeneral);
                    break;
                default:
                    System.out.println("Tipo de empleado desconocido: " + tipo);
            }
        }
        
     // Creamos los diversos tipos de tiquetes y las atracciones que le pertenecen a cada categoria

        atraccionesFamiliares = new ArrayList<Atraccion>();
        atraccionesFamiliares.add(atraccionCultural);

        atraccionesOro = new ArrayList<Atraccion>();
        atraccionesOro.add(atraccionMecanica);

        atraccionesDiamante = new ArrayList<Atraccion>();
        atraccionesDiamante.add(atraccionMecanica);
        atraccionesDiamante.add(atraccionCultural);

        // Creamos los objetos tiquete
        tiqueteFamiliar = new TiqueteFamiliar(atraccionesFamiliares, false);
        tiqueteOro = new TiqueteOro(atraccionesOro, false);
        tiqueteDiamante = new TiqueteDiamante(atraccionesDiamante, false);
        tiqueteBasico = new TiqueteBasico(false);

        /**
         * LECTURA CLIENTES DESDE CSV
         */
        ArrayList<String> lineasClientes = archivoPlano.leer("datos/clientes.csv");

        for (String linea : lineasClientes) {
            // Formato: login,password,nombre,listaTiquetes
            String[] datos = linea.split(",");
            String login = datos[0];
            String password = datos[1];
            String nombre = datos[2];

            ArrayList<Tiquete> listaTiquetes = new ArrayList<>();
            // La lista de tiquetes viene separados por punto y coma
            String[] tiquetesCliente = datos[3].split(";");

            for (String tipo : tiquetesCliente) {
                switch (tipo.trim()) {
                    case "Familiar":
                        listaTiquetes.add(tiqueteFamiliar);
                        break;
                    case "Oro":
                        listaTiquetes.add(tiqueteOro);
                        break;
                    case "Diamante":
                        listaTiquetes.add(tiqueteDiamante);
                        break;
                    case "Basico":
                        listaTiquetes.add(tiqueteBasico);
                        break;
                    default:
                        System.out.println("Tipo de tiquete desconocido: " + tipo);
                }
            }

            Cliente cli = new Cliente(login, password, nombre, listaTiquetes);
            this.clientes.add(cli);
        }
        
        this.br = new BufferedReader(new InputStreamReader(System.in));
        int op;
        
        /**
        MENU PRINCIPAL DE LA APLICACION
        */
        do {
            System.out.println("Digite:\n"
                    + "0. Salir\n"
                    + "1. Consultar requisito atracción\n"
                    + "2. Consultas Trabajadores\n"
                    + "3. Consultar tiquetes\n"
                    + "4. Comprar tiquetes");
            op = Integer.parseInt(leerConsola());
            
            if (op == 1) {
                // Consultas relacionadas con atracciones
                System.out.println("Digite:\n"
                        + "0. Salir\n"
                        + "1. Consultar requisito atracción\n"
                        + "2. Consultar disponibilidad atracción o espectáculo");
                int consulta = Integer.parseInt(leerConsola());
                if (consulta == 0) {
                    op = 0;
                } else if (consulta == 1) {
                    System.out.println("Menú para consultar si un usuario es apto para ingresar a una atracción específica");
                    System.out.println("Digite:\n"
                            + "0. Salir\n"
                            + "1. Atracción Mecánica\n"
                            + "2. Atracción Cultural");
                    int tipoAtraccion = Integer.parseInt(leerConsola());
                    if (tipoAtraccion == 0) {
                        op = 0;
                    } else if (tipoAtraccion == 1) { // Mecánica
                        System.out.println("Ingrese el peso del usuario: ");
                        int pesoUsr = Integer.parseInt(leerConsola());
                        System.out.println("Ingrese la altura del usuario: ");
                        int altUsr = Integer.parseInt(leerConsola());
                        System.out.println("Ingrese las restricciones de salud del usuario: ");
                        String restrUsr = leerConsola();
                        atraccionMecanica.aptaParaCliente(altUsr, pesoUsr, restrUsr);
                        if (!atraccionMecanica.isRespuesta()) {
                            System.out.println("El usuario dado no cumple con los requisitos para entrar a la atracción");
                        } else {
                            System.out.println("El usuario dado puede ingresar a la atracción");
                        }
                    } else if (tipoAtraccion == 2) { // Cultural
                        System.out.println("Ingrese la edad del usuario a consultar: ");
                        int edad = Integer.parseInt(leerConsola());
                        atraccionCultural.aptaParaCliente(edad);
                        if (!atraccionCultural.isRespuesta()) {
                            System.out.println("El usuario dado no cumple con la edad mínima para ingresar a la atracción");
                        } else {
                            System.out.println("El usuario dado puede ingresar a la atracción");
                        }
                    }
                } else if (consulta == 2) {
                    System.out.println("Menú para consultar el estado de una atracción o espectáculo según la temporada y clima");
                    System.out.println("Digite:\n"
                            + "0. Salir\n"
                            + "1. Atracción Mecánica\n"
                            + "2. Atracción Cultural\n"
                            + "3. Espectáculo");
                    int u = Integer.parseInt(leerConsola());
                    if (u == 0) {
                        op = 0;
                    } else if (u == 1) {
                        boolean condicion = atraccionMecanica.estaDisponible(temporada);
                        if (condicion) {
                            System.out.println("La atracción mecánica se encuentra disponible");
                        } else {
                            System.out.println("La atracción mecánica no se encuentra disponible");
                        }
                    } else if (u == 2) {
                        boolean condicion = atraccionCultural.estaDisponible(temporada);
                        if (condicion) {
                            System.out.println("La atracción cultural se encuentra disponible");
                        } else {
                            System.out.println("La atracción cultural no se encuentra disponible");
                        }
                    } else if (u == 3) {
                        boolean condicion = espectaculo.estaDisponible();
                        if (condicion) {
                            System.out.println("El espectáculo se va a realizar");
                        } else {
                            System.out.println("El espectáculo no se va a realizar");
                        }
                    }
                }
            } else if (op == 2) {
                // Consultas relacionadas con los empleados
                System.out.println("Ingrese la opción a realizar:");
                System.out.println("0. Salir");
                System.out.println("1. Consultar turno Empleado");
                System.out.println("2. Registrar Empleado");
                System.out.println("3. Ver Empleados actuales");
                int tipoUsuario = Integer.parseInt(leerConsola());
                if (tipoUsuario == 0) {
                    op = 0;
                } else if (tipoUsuario == 1) {
                    System.out.println("Ingrese el nombre del empleado para verificar si tiene turnos:");
                    String nombreEmpleado = leerConsola();
                    boolean encontrado = false;
                    for (Empleado e : empleados) {
                        if (e.getNombre().equalsIgnoreCase(nombreEmpleado)) {
                            encontrado = true;
                            System.out.println("Ingrese la fecha para consultar turno (YYYY-MM-DD): (Pruebe con: 2025-04-02)");
                            String fechaTexto = leerConsola();
                            try {
                                LocalDate fecha = LocalDate.parse(fechaTexto);
                                Turno turno = e.consultarTurno(fecha);
                                if (turno != null) {
                                    System.out.println("Se encontró un turno para el empleado");
                                    System.out.println("Turno asignado para " + fecha + ":");
                                    System.out.println("Tipo: " + turno.getTipo());
                                    DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
                                    System.out.println("Inicio: " + turno.getHoraInicio().format(formatoHora));
                                    System.out.println("Fin: " + turno.getHoraFin().format(formatoHora));
                                    System.out.println("Lugar de trabajo: " + e.getLugarTrabajo());
                                } else {
                                    System.out.println("No tiene turno asignado para esa fecha.");
                                }
                            } catch (Exception ex) {
                                System.out.println("Fecha inválida. Use el formato YYYY-MM-DD.");
                            }
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Empleado no encontrado.");
                        System.out.println("¿Desea registrarlo? (s/n)");
                        String respuesta = leerConsola();
                        if (respuesta.equalsIgnoreCase("s")) {
                            System.out.println("Ingrese login:");
                            String login = leerConsola();
                            System.out.println("Ingrese contraseña:");
                            String password = leerConsola();
                            System.out.println("Seleccione el tipo de empleado a registrar:");
                            System.out.println("1. Cajero\n2. Cocinero\n3. Operador Mecánico\n4. Servicio General");
                            int tipo = Integer.parseInt(leerConsola());
                            LocalDate fechaTurnoNuevoEmpleado = LocalDate.of(2025, 4, 2); // Ejemplo fijo
                            if (tipo == 1) {
                                Cajero nuevo = new Cajero(login, password, nombreEmpleado, 1, "Taquilla", turnoDiurno, true);
                                nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                                empleados.add(nuevo);
                            } else if (tipo == 2) {
                                Cocinero nuevo = new Cocinero(login, password, nombreEmpleado, 1, "Cocina", turnoDiurno, true);
                                nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                                empleados.add(nuevo);
                            } else if (tipo == 3) {
                                OperadorMecanico nuevo = new OperadorMecanico(login, password, nombreEmpleado, 1, "MontanaRusa", true, atraccionMecanica, turnoDiurno);
                                nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                                empleados.add(nuevo);
                            } else if (tipo == 4) {
                                ServicioGeneral nuevo = new ServicioGeneral(login, password, nombreEmpleado, 1, "Baños", turnoDiurno);
                                nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                                empleados.add(nuevo);
                            } else {
                                System.out.println("Tipo de empleado inválido.");
                                return;
                            }
                            System.out.println("Empleado registrado exitosamente.");
                            
                            ArrayList<String> lineasEmpleado = new ArrayList<>();
                            for (Empleado empleado : empleados) {
                                StringBuilder sb = new StringBuilder();
                                // Tipo de empleado, nombre de la subclase
                                sb.append(empleado.getClass().getSimpleName()).append(",");
                                sb.append(empleado.getLogin()).append(",");
                                sb.append(empleado.getPassword()).append(",");
                                sb.append(empleado.getNombre()).append(",");
                                sb.append(empleado.getId()).append(",");
                                sb.append(empleado.getLugarTrabajo()).append(",");
                                sb.append(empleado.getTurno());
                                lineasEmpleado.add(sb.toString());
                            }
                            
                            ArchivoPlano archivoPlano = new ArchivoPlano();
                            archivoPlano.escribir("datos/empleados.csv", lineasEmpleado);
                        } else {
                            System.out.println("Proceso cancelado.");
                            return;
                        }
                    }
                } else if (tipoUsuario == 2) {
                    // Registro manual de empleado
                    System.out.println("Ingrese el nombre:");
                    String nombreEmpleado = leerConsola();
                    System.out.println("Ingrese login:");
                    String login = leerConsola();
                    System.out.println("Ingrese contraseña:");
                    String password = leerConsola();
                    System.out.println("Seleccione el tipo de empleado a registrar:");
                    System.out.println("1. Cajero\n2. Cocinero\n3. Operador Mecánico\n4. Servicio General");
                    int tipo = Integer.parseInt(leerConsola());
                    LocalDate fechaTurnoNuevoEmpleado = LocalDate.of(2025, 4, 2);
                    if (tipo == 1) {
                        Cajero nuevo = new Cajero(login, password, nombreEmpleado, 1, "Taquilla", turnoDiurno, true);
                        nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                        empleados.add(nuevo);
                    } else if (tipo == 2) {
                        Cocinero nuevo = new Cocinero(login, password, nombreEmpleado, 1, "Cocina", turnoDiurno, true);
                        nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                        empleados.add(nuevo);
                    } else if (tipo == 3) {
                        OperadorMecanico nuevo = new OperadorMecanico(login, password, nombreEmpleado, 1, "MontanaRusa", true, atraccionMecanica, turnoDiurno);
                        nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                        empleados.add(nuevo);
                    } else if (tipo == 4) {
                        ServicioGeneral nuevo = new ServicioGeneral(login, password, nombreEmpleado, 1, "Baños", turnoDiurno);
                        nuevo.asignarTurno(fechaTurnoNuevoEmpleado, turnoDiurno);
                        empleados.add(nuevo);
                    } else {
                        System.out.println("Tipo de empleado inválido.");
                        return;
                    }
                    System.out.println("Empleado registrado exitosamente.");
                    
                    ArrayList<String> lineasEmpleado = new ArrayList<>();
                    for (Empleado empleado : empleados) {
                        StringBuilder sb = new StringBuilder();
                        // Tipo de empleado, nombre de la subclase
                        sb.append(empleado.getClass().getSimpleName()).append(",");
                        sb.append(empleado.getLogin()).append(",");
                        sb.append(empleado.getPassword()).append(",");
                        sb.append(empleado.getNombre()).append(",");
                        sb.append(empleado.getId()).append(",");
                        sb.append(empleado.getLugarTrabajo()).append(",");
                        sb.append(empleado.getTurno());
                        lineasEmpleado.add(sb.toString());
                    }
                    
                    ArchivoPlano archivoPlano = new ArchivoPlano();
                    archivoPlano.escribir("datos/empleados.csv", lineasEmpleado);
                    
                } else if (tipoUsuario == 3) {
                    if (empleados.isEmpty()) {
                        System.out.println("No hay empleados registrados.");
                    } else {
                        System.out.println("Empleados registrados:");
                        for (Empleado e : empleados) {
                            System.out.println("- " + e.getNombre());
                        }
                    }
                }
            } else if (op == 3) {
                // Consultas de tiquetes
                System.out.println("Menú de consulta de tiquetes");
                System.out.println("0. Salir");
                System.out.println("1. Ver todos los clientes registrados");
                System.out.println("2. Ver tiquetes de un cliente específico");
                int subop = Integer.parseInt(leerConsola());
                if (subop == 1) {
                    if (clientes.isEmpty()) {
                        System.out.println("No hay clientes registrados.");
                    } else {
                        System.out.println("Clientes registrados:");
                        for (Cliente c : clientes) {
                            System.out.println("- " + c.getNombre());
                        }
                    }
                } else if (subop == 2) {
                    System.out.println("Ingrese el nombre del cliente:");
                    String nombreCliente = leerConsola();
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
                } else {
                    System.out.println("Opción no válida.");
                }
            } else if (op == 4) {
                // Compra de tiquetes
                ArrayList<Tiquete> tiqueteCliente = new ArrayList<>();
                System.out.println("Ingrese el nombre del cliente que desea comprar un tiquete:");
                String nombreCliente = leerConsola();
                Cliente clienteEncontrado = null;
                for (Cliente c : clientes) {
                    if (c.getNombre().equalsIgnoreCase(nombreCliente)) {
                        clienteEncontrado = c;
                        break;
                    }
                }
                if (clienteEncontrado == null) {
                    System.out.println("Cliente no encontrado. ¿Desea registrarlo? (s/n)");
                    String respuesta = leerConsola();
                    if (respuesta.equalsIgnoreCase("s")) {
                        System.out.println("Ingrese login:");
                        String login = leerConsola();
                        System.out.println("Ingrese contraseña:");
                        String password = leerConsola();
                        clienteEncontrado = new Cliente(login, password, nombreCliente, tiqueteCliente);
                        clientes.add(clienteEncontrado);
                        System.out.println("Cliente registrado exitosamente.");
                    } else {
                        System.out.println("Venta cancelada.");
                        return;
                    }
                }
                
                System.out.println("¿Desea agregar FastPass? (s/n)");
                boolean fastPass = leerConsola().equalsIgnoreCase("s");
                System.out.println("Seleccione el tipo de tiquete a comprar:");
                System.out.println("1. Básico");
                System.out.println("2. Familiar");
                System.out.println("3. Oro");
                System.out.println("4. Diamante");
                int tipo = Integer.parseInt(leerConsola());
                List<Atraccion> atraccionesVacias = new ArrayList<>(); // Simulación temporal
                Tiquete nuevo;
                if (tipo == 1) {
                    nuevo = new TiqueteBasico(fastPass);
                    tiqueteCliente.add(nuevo);
                } else if (tipo == 2) {
                    nuevo = new TiqueteFamiliar(atraccionesVacias, fastPass);
                    tiqueteCliente.add(nuevo);
                } else if (tipo == 3) {
                    nuevo = new TiqueteOro(atraccionesVacias, fastPass);
                    tiqueteCliente.add(nuevo);
                } else if (tipo == 4) {
                    nuevo = new TiqueteDiamante(atraccionesVacias, fastPass);
                    tiqueteCliente.add(nuevo);
                } else {
                    System.out.println("Tipo de tiquete inválido.");
                    return;
                }
                System.out.println("Tiquete agregado exitosamente al cliente " + clienteEncontrado.getNombre());
                
                /**
                ESCRITURA CLIENTES AL CSV
                */
                ArrayList<String> lineasCliente = new ArrayList<>();
                
                for (Cliente cliente : clientes) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cliente.getLogin()).append(",");
                    sb.append(cliente.getPassword()).append(",");
                    sb.append(cliente.getNombre()).append(",");

                    ArrayList<String> nombresTiquetes = new ArrayList<>();
                    for (Tiquete t : cliente.getTiquetes()) {
                        nombresTiquetes.add(t.getClass().getSimpleName().replace("Tiquete", "")); // Ej: TiqueteOro → Oro
                    }
                    sb.append(String.join(";", nombresTiquetes));
                    
                    lineasCliente.add(sb.toString());
                }
                
                ArchivoPlano archivoPlano = new ArchivoPlano();
                archivoPlano.escribir("datos/clientes.csv", lineasCliente);
            }
        } while (op != 0);
		try {
			this.br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String leerConsola() {
		String dato = null;
		try {
			dato = this.br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dato;
	}

	public static void main(String[] args) {
		new Principal();
	}
}