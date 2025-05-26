package Interfaz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import atracciones.AtraccionMecanica;
import atracciones.Atraccion;
import atracciones.AtraccionCultural;

import persistencia.ArchivoPlano;
import persona.Cajero;
import persona.Cocinero;
import persona.Empleado;
import persona.OperadorMecanico;
import persona.ServicioGeneral;
import persona.Turno;

public class InterfazAdmin {
	private Scanner scanner = new Scanner(System.in);
	private AtraccionMecanica atraccionMecanica;
	private AtraccionCultural atraccionCultural;
	private List<Atraccion> atracciones;
	private List<Turno> turnosDisponibles = new ArrayList<>();
	private ArrayList<Empleado> empleados = new ArrayList<>();
	
	
	// Objeto para lectura de archivos
    private ArchivoPlano archivoPlano;
    public InterfazAdmin() {
        this.atracciones = new ArrayList<>();
        this.turnosDisponibles = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.archivoPlano = new ArchivoPlano();
    }
    
    /**
	 * VERIFICACION DE LA INFORMACION DEL ADMINISTRADOR
	 */
    
    public void autenticarYIniciar() {
        System.out.println("== AUTENTICACION DE ADMINISTRADOR ==");

        System.out.print("Usuario (admin1): ");
        String usuarioIngresado = scanner.nextLine();

        System.out.print("Contraseña (adminpass): ");
        String contraseñaIngresada = scanner.nextLine();

        archivoPlano = new ArchivoPlano();
        ArrayList<String> empleados = archivoPlano.leer("datos/empleados.csv");

        boolean autenticado = false;

        for (String linea : empleados) {
            String[] partes = linea.split(",");
            if (partes.length >= 3) {
                String rol = partes[0];
                String usuario = partes[1];
                String contraseña = partes[2];

                if (rol.equalsIgnoreCase("Administrador") &&
                    usuario.equals(usuarioIngresado) &&
                    contraseña.equals(contraseñaIngresada)) {
                    autenticado = true;
                    break;
                }
            }
        }

        if (autenticado) {
            System.out.println("Autenticacion exitosa");
            iniciar();
        } else {
            System.out.println("Usuario o contraseña incorrectos. Acceso denegado.");
        }
    }

	public void iniciar() {
		
		this.atracciones = new ArrayList<>();
		
		/**
		 * LECTURA DE LAS ATRACCIONES DEL ARCHIVO
		 */
		
		this.archivoPlano = new ArchivoPlano();
		
		System.out.println("Cargando atracciones existentes...");
		
		ArrayList<String> atraccionesExistentes = archivoPlano.leer("datos/atracciones.csv");

		for (String linea : atraccionesExistentes) {
		    // Saltar líneas vacías o mal formadas para evitar fallos
		    if (linea.trim().isEmpty()) continue;

		    String[] datos = linea.split(",");
		    
		    if (datos.length < 6) {
		        System.out.println("Linea invalida (campos insuficientes): " + linea);
		        continue;
		    }

		    String tipoExistente = datos[0];
		    String nombreExistente = datos[1];
		    int cupoMaximoExistente = Integer.parseInt(datos[2]);
		    int empleadosRegistradosExistente = Integer.parseInt(datos[3]);
		    boolean disponibleClimaExistente = Boolean.parseBoolean(datos[4]);
		    String nivelExclusividadExistente = datos[5];

		    switch (tipoExistente) {
		        case "Mecanica":
		            if (datos.length < 12) {
		                System.out.println("Linea invalida para atraccion mecanica: " + linea);
		                continue;
		            }
		            int minimoAlturaExistente = Integer.parseInt(datos[6]);
		            int maximoAlturaExistente = Integer.parseInt(datos[7]);
		            int minimoPesoExistente = Integer.parseInt(datos[8]);
		            int maximoPesoExistente = Integer.parseInt(datos[9]);
		            String restriccionesSaludExistente = datos[10];
		            String nivelRiesgoExistente = datos[11];

		            atraccionMecanica = new AtraccionMecanica(
		                nombreExistente, cupoMaximoExistente, empleadosRegistradosExistente,
		                disponibleClimaExistente, nivelExclusividadExistente,
		                minimoAlturaExistente, maximoAlturaExistente,
		                minimoPesoExistente, maximoPesoExistente,
		                restriccionesSaludExistente, nivelRiesgoExistente
		            );
		            this.atracciones.add(atraccionMecanica);
		            break;

		        case "Cultural":
		            if (datos.length < 7) {
		                System.out.println("Linea invalida para atracción cultural: " + linea);
		                continue;
		            }
		            int edad = Integer.parseInt(datos[6]);

		            atraccionCultural = new AtraccionCultural(
		                nombreExistente, cupoMaximoExistente, empleadosRegistradosExistente,
		                disponibleClimaExistente, nivelExclusividadExistente, edad
		            );
		            this.atracciones.add(atraccionCultural);
		            break;

		        default:
		            System.out.println("Tipo de atraccion desconocido: " + tipoExistente);
		    }
		}
		
		System.out.println("Las atracciones se han cargado correctamente");
		
		while (true) {
			
			System.out.println("       === MENU ADMINISTRADOR ===       ");
			System.out.println(" 1. Crear atraccion                   ");
			System.out.println(" 2. Modificar atraccion               ");
			System.out.println(" 3. Eliminar atraccion                ");
			System.out.println(" 4. Crear nuevo turno                 ");
			System.out.println(" 5. Asignar turno a empleado          ");
			System.out.println(" 6. Crear un nuevo empleado           ");
			System.out.println(" 0. Salir                             ");
			System.out.print ("Seleccione una opcion: ");
			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) crearAtraccion();
			else if (opcion == 2) modificarAtraccion();
			else if (opcion == 3) eliminarAtraccion();
			else if (opcion == 4) crearTurno();
			else if (opcion == 5) AsignarNuevoTurno();
			else if (opcion == 6) CrearEmpleado();
			else if (opcion == 0) break;
			else System.out.println("Opcion invalida.");
		}
	}

	public void crearAtraccion() {
		/**
         * MENU QUE PERMITE CREAR UNA NUEVA ATRACCION
         */
	    System.out.println("Seleccione el tipo de atraccion que desea registrar:");
	    System.out.println("1. Atraccion Mecanica");
	    System.out.println("2. Atraccion Cultural");
	    int opcion = scanner.nextInt();
	    scanner.nextLine();

	    Atraccion nuevaAtraccion = null;

	    System.out.print("Ingrese el nombre de la atraccion: ");
	    String nombre = scanner.nextLine();

	    // verificamos que el nombre de la nueva atraccion sea nuevo
	    for (Atraccion a : atracciones) {
	        if (a.getNombre().equalsIgnoreCase(nombre)) {
	            System.out.println("Ya existe una atraccion registrada con ese nombre.");
	            return;
	        }
	    }

	    System.out.print("Ingrese el cupo maximo de la atraccion: ");
	    int cupoMaximo = scanner.nextInt();

	    System.out.print("Ingrese la cantidad de empleados encargados: ");
	    int empleadosEncargados = scanner.nextInt();

	    System.out.print("¿La atraccion esta disponible en condiciones climaticas adversas? (true/false): ");
	    boolean disponibleClima = scanner.nextBoolean();
	    scanner.nextLine();

	    System.out.print("Ingrese el nivel de exclusividad de la atraccion (por ejemplo: familiar, oro, diamante): ");
	    String nivelExclusividad = scanner.nextLine();

	    if (opcion == 1) {
	    	/**
	         * REGISTRO DE UNA ATRACCION MECANICA
	         */
	        System.out.print("Ingrese la altura minima permitida (en cm): ");
	        int minimoAltura = scanner.nextInt();

	        System.out.print("Ingrese la altura maxima permitida (en cm): ");
	        int maximoAltura = scanner.nextInt();

	        System.out.print("Ingrese el peso minimo permitido (en kg): ");
	        int minimoPeso = scanner.nextInt();

	        System.out.print("Ingrese el peso maximo permitido (en kg): ");
	        int maximoPeso = scanner.nextInt();
	        scanner.nextLine();

	        System.out.print("Ingrese las restricciones de salud (Ejemplo: vertigo, marcapasos): ");
	        String restriccionesSalud = scanner.nextLine();

	        System.out.print("Ingrese el nivel de riesgo de la atracción (bajo, medio o alto): ");
	        String nivelRiesgo = scanner.nextLine();

	        nuevaAtraccion = new AtraccionMecanica(
	            nombre, cupoMaximo, empleadosEncargados, disponibleClima,
	            nivelExclusividad, minimoAltura, maximoAltura,
	            minimoPeso, maximoPeso, restriccionesSalud, nivelRiesgo
	        );

	    } else if (opcion == 2) {
	    	/**
	         * REGISTRO DE UNA ATRACCION CULTURAL
	         */
	        System.out.print("Ingrese la edad minima requerida para acceder a la atraccion: ");
	        int edadMinima = scanner.nextInt();
	        scanner.nextLine();

	        nuevaAtraccion = new AtraccionCultural(
	            nombre, cupoMaximo, empleadosEncargados, disponibleClima,
	            nivelExclusividad, edadMinima
	        );
	    } else {
	        System.out.println("Opcion invalida. No se pudo registrar la atraccion.");
	        return;
	    }

	    atracciones.add(nuevaAtraccion);
	    System.out.println("La atraccion ha sido registrada exitosamente: " + nombre);

	    guardarAtracciones();
	}
	
	public void crearAtraccionDesdeSwing(String tipo, String nombre, String cupoStr, String empleadosStr,
            String climaStr, String nivelExclusividad,
            String... extras) {
		try {
			// Validar campos comunes
			int cupoMaximo = Integer.parseInt(cupoStr.trim());
			int empleadosEncargados = Integer.parseInt(empleadosStr.trim());
			boolean disponibleClima = Boolean.parseBoolean(climaStr.trim());

			// Verificar que no exista una atracción con ese nombre
			for (Atraccion a : atracciones) {
			if (a.getNombre().equalsIgnoreCase(nombre)) {
			JOptionPane.showMessageDialog(null,
			"Ya existe una atracción registrada con ese nombre.",
			"Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
			}
			Atraccion nuevaAtraccion;

			if (tipo.equalsIgnoreCase("Mecanica")) {
			if (extras.length < 6) {
			JOptionPane.showMessageDialog(null,
			"Faltan datos para atracción mecánica.",
			"Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
			int minAltura = Integer.parseInt(extras[0].trim());
			int maxAltura = Integer.parseInt(extras[1].trim());
			int minPeso = Integer.parseInt(extras[2].trim());
			int maxPeso = Integer.parseInt(extras[3].trim());
			String restriccionesSalud = extras[4].trim();
			String nivelRiesgo = extras[5].trim();

			nuevaAtraccion = new AtraccionMecanica(
			nombre, cupoMaximo, empleadosEncargados, disponibleClima,
			nivelExclusividad, minAltura, maxAltura,
			minPeso, maxPeso, restriccionesSalud, nivelRiesgo
			);
			} else {
			if (extras.length < 1) {
			JOptionPane.showMessageDialog(null,
			"Falta la edad mínima para atracción cultural.",
			"Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
			int edadMinima = Integer.parseInt(extras[0].trim());

			nuevaAtraccion = new AtraccionCultural(
			nombre, cupoMaximo, empleadosEncargados,
			disponibleClima, nivelExclusividad, edadMinima
			);
			}

			atracciones.add(nuevaAtraccion);
			guardarAtracciones();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
			"Error en los datos numéricos: " + e.getMessage(),
			"Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
			"Error inesperado: " + e.getMessage(),
			"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	
	public void modificarAtraccionDesdeSwing(String nombreModificar) {
	    archivoPlano = new ArchivoPlano();
	    ArrayList<String> atraccionesActuales = archivoPlano.leer("datos/atracciones.csv");

	    boolean encontrada = false;
	    ArrayList<String> atraccionesModificadas = new ArrayList<>();

	    for (String linea : atraccionesActuales) {
	        String[] partes = linea.split(",");

	        if (partes.length >= 6 && partes[1].equalsIgnoreCase(nombreModificar)) {
	            encontrada = true;

	            // Mostrar información actual en consola de respaldo
	            System.out.println("Modificando atraccion: " + linea);

	            String tipo = partes[0];
	            String cupoMaximo = JOptionPane.showInputDialog(null, "Nuevo cupo máximo:", partes[2]);
	            String empleadosEncargados = JOptionPane.showInputDialog(null, "Cantidad de empleados:", partes[3]);
	            String disponibleClima = JOptionPane.showInputDialog(null, "¿Disponible en clima adverso? (true/false):", partes[4]);
	            String nivelExclusividad = JOptionPane.showInputDialog(null, "Nivel de exclusividad:", partes[5]);

	            if (tipo.equalsIgnoreCase("Mecanica") && partes.length >= 12) {
	                String minAltura = JOptionPane.showInputDialog(null, "Altura mínima (cm):", partes[6]);
	                String maxAltura = JOptionPane.showInputDialog(null, "Altura máxima (cm):", partes[7]);
	                String minPeso = JOptionPane.showInputDialog(null, "Peso mínimo (kg):", partes[8]);
	                String maxPeso = JOptionPane.showInputDialog(null, "Peso máximo (kg):", partes[9]);
	                String salud = JOptionPane.showInputDialog(null, "Restricciones de salud:", partes[10]);
	                String riesgo = JOptionPane.showInputDialog(null, "Nivel de riesgo:", partes[11]);

	                String nuevaLinea = String.join(",", tipo, nombreModificar, cupoMaximo, empleadosEncargados,
	                        disponibleClima, nivelExclusividad, minAltura, maxAltura, minPeso, maxPeso, salud, riesgo);

	                atraccionesModificadas.add(nuevaLinea);
	            } else if (tipo.equalsIgnoreCase("Cultural") && partes.length >= 7) {
	                String edadMinima = JOptionPane.showInputDialog(null, "Edad mínima requerida:", partes[6]);

	                String nuevaLinea = String.join(",", tipo, nombreModificar, cupoMaximo, empleadosEncargados,
	                        disponibleClima, nivelExclusividad, edadMinima);

	                atraccionesModificadas.add(nuevaLinea);
	            } else {
	                JOptionPane.showMessageDialog(null, "Formato desconocido para esta atracción.");
	                return;
	            }

	        } else {
	            atraccionesModificadas.add(linea);
	        }
	    }

	    if (encontrada) {
	        archivoPlano.escribir("datos/atracciones.csv", atraccionesModificadas);
	        JOptionPane.showMessageDialog(null, "Atracción modificada exitosamente.");
	    } else {
	        JOptionPane.showMessageDialog(null, "No se encontró ninguna atracción con ese nombre.");
	    }
	}
	
	public void eliminarAtraccionDesdeSwing(String nombreAEliminar) {
	    archivoPlano = new ArchivoPlano();
	    ArrayList<String> atraccionesActuales = archivoPlano.leer("datos/atracciones.csv");

	    boolean encontrada = false;
	    ArrayList<String> atraccionesActualizadas = new ArrayList<>();

	    for (String linea : atraccionesActuales) {
	        String[] partes = linea.split(",");
	        if (partes.length >= 2 && partes[1].equalsIgnoreCase(nombreAEliminar)) {
	            encontrada = true;
	        } else {
	            atraccionesActualizadas.add(linea);
	        }
	    }

	    if (encontrada) {
	        archivoPlano.escribir("datos/atracciones.csv", atraccionesActualizadas);
	        JOptionPane.showMessageDialog(null, "Atracción eliminada exitosamente: " + nombreAEliminar);
	    } else {
	        JOptionPane.showMessageDialog(null, "No se encontró una atracción con ese nombre.");
	    }
	}
	
	public void crearTurnoDesdeSwing() {
	    try {
	        String tipo = JOptionPane.showInputDialog(null, "Tipo de turno (Diurno o Nocturno):");
	        if (tipo == null || tipo.trim().isEmpty()) return;

	        String inicioStr = JOptionPane.showInputDialog(null, "Fecha y hora de inicio (yyyy-MM-dd HH:mm):");
	        if (inicioStr == null || inicioStr.trim().isEmpty()) return;

	        String finStr = JOptionPane.showInputDialog(null, "Fecha y hora de fin (yyyy-MM-dd HH:mm):");
	        if (finStr == null || finStr.trim().isEmpty()) return;

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        LocalDateTime inicio = LocalDateTime.parse(inicioStr.trim(), formatter);
	        LocalDateTime fin = LocalDateTime.parse(finStr.trim(), formatter);

	        Turno nuevoTurno = new Turno(tipo.trim(), inicio, fin);
	        turnosDisponibles.add(nuevoTurno);

	        JOptionPane.showMessageDialog(null, "Turno creado exitosamente:\n" + nuevoTurno.toString());

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null,
	                "Error al crear el turno. Asegúrese de ingresar los datos en el formato correcto.",
	                "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	public void asignarTurnoDesdeSwing() {
	    archivoPlano = new ArchivoPlano();
	    ArrayList<String> empleadosActuales = archivoPlano.leer("datos/empleados.csv");

	    if (empleadosActuales.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay empleados registrados.");
	        return;
	    }

	    // Mostrar empleados
	    String[] opcionesEmpleados = new String[empleadosActuales.size()];
	    for (int i = 0; i < empleadosActuales.size(); i++) {
	        String[] partes = empleadosActuales.get(i).split(",");
	        opcionesEmpleados[i] = partes.length >= 4 ? partes[3] + " (ID: " + partes[4] + ")" : "Empleado " + (i + 1);
	    }

	    String seleccionadoEmpleado = (String) JOptionPane.showInputDialog(null,
	            "Seleccione el empleado:",
	            "Asignar Turno",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            opcionesEmpleados,
	            opcionesEmpleados[0]);

	    if (seleccionadoEmpleado == null) return;

	    if (turnosDisponibles.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No hay turnos disponibles. Cree uno primero.");
	        return;
	    }

	    // Mostrar turnos disponibles
	    String[] opcionesTurnos = new String[turnosDisponibles.size()];
	    for (int i = 0; i < turnosDisponibles.size(); i++) {
	        opcionesTurnos[i] = turnosDisponibles.get(i).toString();
	    }

	    String seleccionadoTurno = (String) JOptionPane.showInputDialog(null,
	            "Seleccione el turno a asignar:",
	            "Asignar Turno",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            opcionesTurnos,
	            opcionesTurnos[0]);

	    if (seleccionadoTurno == null) return;

	    // Buscar índices seleccionados
	    int indexEmpleado = -1, indexTurno = -1;
	    for (int i = 0; i < empleadosActuales.size(); i++) {
	        if (opcionesEmpleados[i].equals(seleccionadoEmpleado)) {
	            indexEmpleado = i;
	            break;
	        }
	    }

	    for (int i = 0; i < turnosDisponibles.size(); i++) {
	        if (turnosDisponibles.get(i).toString().equals(seleccionadoTurno)) {
	            indexTurno = i;
	            break;
	        }
	    }

	    if (indexEmpleado == -1 || indexTurno == -1) {
	        JOptionPane.showMessageDialog(null, "No se pudo asignar el turno.");
	        return;
	    }

	    // Actualizar archivo CSV
	    String lineaOriginal = empleadosActuales.get(indexEmpleado);
	    String[] partes = lineaOriginal.split(",");
	    if (partes.length < 7) {
	        JOptionPane.showMessageDialog(null, "Error al procesar el empleado.");
	        return;
	    }

	    partes[6] = turnosDisponibles.get(indexTurno).toString();
	    empleadosActuales.set(indexEmpleado, String.join(",", partes));
	    archivoPlano.escribir("datos/empleados.csv", empleadosActuales);

	    JOptionPane.showMessageDialog(null, "Turno asignado exitosamente a " + partes[3]);
	}
	
	public void registrarEmpleadoDesdeSwing() {
	    try {
	        String nombre = JOptionPane.showInputDialog(null, "Nombre del empleado:");
	        if (nombre == null || nombre.trim().isEmpty()) return;

	        String login = JOptionPane.showInputDialog(null, "Login del empleado:");
	        if (login == null || login.trim().isEmpty()) return;

	        String password = JOptionPane.showInputDialog(null, "Contraseña:");
	        if (password == null || password.trim().isEmpty()) return;

	        String[] tipos = {"Cajero", "Cocinero", "Operador Mecanico", "Servicio General"};
	        String tipoSeleccionado = (String) JOptionPane.showInputDialog(null,
	                "Seleccione el tipo de empleado:",
	                "Tipo de Empleado",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                tipos,
	                tipos[0]);

	        if (tipoSeleccionado == null) return;

	        // Validar turno disponible
	        if (turnosDisponibles.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No hay turnos disponibles. Cree uno primero.");
	            return;
	        }

	        // Selección de turno
	        String[] opcionesTurnos = new String[turnosDisponibles.size()];
	        for (int i = 0; i < turnosDisponibles.size(); i++) {
	            opcionesTurnos[i] = turnosDisponibles.get(i).toString();
	        }

	        String seleccionadoTurno = (String) JOptionPane.showInputDialog(null,
	                "Seleccione un turno para el empleado:",
	                "Turno",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opcionesTurnos,
	                opcionesTurnos[0]);

	        if (seleccionadoTurno == null) return;

	        Turno turnoSeleccionado = null;
	        for (Turno t : turnosDisponibles) {
	            if (t.toString().equals(seleccionadoTurno)) {
	                turnoSeleccionado = t;
	                break;
	            }
	        }

	        Empleado nuevo = switch (tipoSeleccionado) {
	            case "Cajero" -> new Cajero(login, password, nombre, 1, "Taquilla", turnoSeleccionado, true);
	            case "Cocinero" -> new Cocinero(login, password, nombre, 1, "Cocina", turnoSeleccionado, true);
	            case "Operador Mecanico" -> new OperadorMecanico(login, password, nombre, 1, "MontanaRusa", true, atraccionMecanica, turnoSeleccionado);
	            case "Servicio General" -> new ServicioGeneral(login, password, nombre, 1, "Baños", turnoSeleccionado);
	            default -> null;
	        };

	        if (nuevo == null) {
	            JOptionPane.showMessageDialog(null, "Tipo de empleado no reconocido.");
	            return;
	        }

	        nuevo.asignarTurno(turnoSeleccionado.getHoraInicio().toLocalDate(), turnoSeleccionado);
	        nuevo.setTurno(turnoSeleccionado);
	        empleados.add(nuevo);
	        archivoPlano = new ArchivoPlano();
	        archivoPlano.escribirEmpleadoAppend("datos/empleados.csv", nuevo);

	        JOptionPane.showMessageDialog(null, "Empleado registrado exitosamente.");
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Error al registrar empleado: " + e.getMessage());
	    }
	}

	/**
     * FUNCION PARA GUARDAR LOS CAMBIOS HECHOS EN EL ARCHIVO
     */
	private void guardarAtracciones() {
	    ArrayList<String> lineasAtracciones = new ArrayList<>();

	    for (Atraccion atraccion : atracciones) {
	        StringBuilder sb = new StringBuilder();
	        sb.append(atraccion instanceof AtraccionMecanica ? "Mecanica" : "Cultural").append(",");
	        sb.append(atraccion.getNombre()).append(",");
	        sb.append(atraccion.getCupoMaximo()).append(",");
	        sb.append(atraccion.getEmpleadosEncargados()).append(",");
	        sb.append(atraccion.isDisponibleClima()).append(",");
	        sb.append(atraccion.getNivelExclusividad()).append(",");

	        if (atraccion instanceof AtraccionMecanica mecanica) {
	            sb.append(mecanica.getMinimoAltura()).append(",");
	            sb.append(mecanica.getMaximoAltura()).append(",");
	            sb.append(mecanica.getMinimoPeso()).append(",");
	            sb.append(mecanica.getMaximoPeso()).append(",");
	            sb.append(mecanica.getRestriccionesSalud()).append(",");
	            sb.append(mecanica.getNivelRiesgo());
	        } else if (atraccion instanceof AtraccionCultural cultural) {
	            sb.append(cultural.getEdadMinima());
	        }

	        lineasAtracciones.add(sb.toString());
	    }

	    archivoPlano.escribir("datos/atracciones.csv", lineasAtracciones);
	}

	/**
     * FUNCION PARA MODIFICAR LA INFORMACION DE UNA ATRACCION EN ESPECIFICO
     */
	public void modificarAtraccion() {
		System.out.println("== Modificar atraccion ==");

		archivoPlano = new ArchivoPlano();
		ArrayList<String> atraccionesActuales = archivoPlano.leer("datos/atracciones.csv");

		// Muestra el nombre de todas las atracciones para mayor facilidad
		System.out.println("\nAtracciones actuales:");
		for (String linea : atraccionesActuales) {
		    String[] partes = linea.split(",");
		    if (partes.length > 1) {
		        System.out.println("- " + partes[1]);
		    }
		}

		System.out.print("\nIngrese el nombre de la atracción a modificar: ");
		String nombreModificar = scanner.nextLine();

		boolean encontrada = false;
		ArrayList<String> atraccionesModificadas = new ArrayList<>();

	    for (String linea : atraccionesActuales) {
	        String[] partes = linea.split(",");

	        if (partes.length >= 12 && partes[1].equalsIgnoreCase(nombreModificar)) {
	            encontrada = true;

	            // Mostramos los datos actuales
	            System.out.println("\nDatos actuales de la atraccion:");
	            System.out.println("Atraccion de tipo: " + partes[0]);
	            System.out.println("Nombre: " + partes[1]);
	            System.out.println("Cupo máximo: " + partes[2]);
	            System.out.println("Empleados encargados: " + partes[3]);
	            System.out.println("Disponible con clima adverso: " + partes[4]);
	            System.out.println("Nivel de exclusividad: " + partes[5]);
	            System.out.println("Altura minima (cm): " + partes[6]);
	            System.out.println("Altura máxima (cm): " + partes[7]);
	            System.out.println("Peso minimo (kg): " + partes[8]);
	            System.out.println("Peso maximo (kg): " + partes[9]);
	            System.out.println("Restricciones de salud: " + partes[10]);
	            System.out.println("Nivel de riesgo: " + partes[11]);

	            // Solicitamos los nuevos datos
	            System.out.println("\n== Ingrese los nuevos datos de la atraccion ==");

	            System.out.print("Ingrese el tipo de atraccion (Cultural/Mecanica): ");
	            String tipo = scanner.nextLine();
	            String tipoAtraccion = "";
	            if (tipo.equalsIgnoreCase("Cultural")) {
	                tipoAtraccion = "Cultural";
	            } else if (tipo.equalsIgnoreCase("Mecanica")) {
	                tipoAtraccion = "Mecanica";
	            }

	            System.out.print("Ingrese el cupo máximo de la atracción: ");
	            int cupoMaximo = scanner.nextInt();

	            System.out.print("Ingrese la cantidad de empleados encargados: ");
	            int empleadosEncargados = scanner.nextInt();

	            System.out.print("¿Está disponible con clima adverso? (true/false): ");
	            boolean disponibleClima = scanner.nextBoolean();
	            scanner.nextLine();

	            System.out.print("Ingrese el nivel de exclusividad: ");
	            String nivelExclusividad = scanner.nextLine();

	            System.out.print("Ingrese la altura minima (cm): ");
	            int minAltura = scanner.nextInt();

	            System.out.print("Ingrese la altura maxima (cm): ");
	            int maxAltura = scanner.nextInt();

	            System.out.print("Ingrese el peso minimo (kg): ");
	            int minPeso = scanner.nextInt();

	            System.out.print("Ingrese el peso maximo (kg): ");
	            int maxPeso = scanner.nextInt();
	            scanner.nextLine();

	            System.out.print("Ingrese las restricciones de salud: ");
	            String restricciones = scanner.nextLine();

	            System.out.print("Ingrese el nivel de riesgo: ");
	            String riesgo = scanner.nextLine();

	            String nuevaLinea = tipoAtraccion + "," + nombreModificar + "," + cupoMaximo + "," + empleadosEncargados + "," +
	                                disponibleClima + "," + nivelExclusividad + "," + minAltura + "," + maxAltura + "," +
	                                minPeso + "," + maxPeso + "," + restricciones + "," + riesgo;

	            atraccionesModificadas.add(nuevaLinea);
	        } else {
	            atraccionesModificadas.add(linea);
	        }
	    }

	    if (encontrada) {
	        archivoPlano.escribir("datos/atracciones.csv", atraccionesModificadas);
	        System.out.println("\nLa atraccion ha sido modificada exitosamente.");
	    } else {
	        System.out.println("\nNo se encontro ninguna atraccion registrada con ese nombre.");
	    }
	}

	/**
     * FUNCION PARA ELIMINAR UNA ATRACCION EN ESPECIFICO
     */
	public void eliminarAtraccion() {
	    System.out.println("== Eliminar atraccion ==");

	    System.out.print("Ingrese el nombre de la atraccion que desea eliminar: ");
	    String nombreAEliminar = scanner.nextLine();

	    archivoPlano = new ArchivoPlano();
	    ArrayList<String> atraccionesActuales = archivoPlano.leer("datos/atracciones.csv");

	    boolean encontrada = false;
	    ArrayList<String> atraccionesActualizadas = new ArrayList<>();

	    for (String linea : atraccionesActuales) {
	        String[] partes = linea.split(",");
	        if (partes.length >= 2 && partes[1].equalsIgnoreCase(nombreAEliminar)) {
	            encontrada = true;
	        } else {
	            atraccionesActualizadas.add(linea);
	        }
	    }

	    if (encontrada) {
	        archivoPlano.escribir("datos/atracciones.csv", atraccionesActualizadas);
	        System.out.println("Atraccion eliminada exitosamente: " + nombreAEliminar);
	    } else {
	        System.out.println("No se encontro una atracción con ese nombre.");
	    }
	}
	/**
     * FUNCION QUE PERMITE CREAR UN TURNO
     */
	public void crearTurno() {
	    System.out.println("\n== Crear nuevo turno ==");

	    System.out.print("Ingrese el tipo de turno (Diurno o Nocturno): ");
	    String tipo = scanner.nextLine();

	    System.out.println("Ingrese la fecha y hora de inicio del turno (formato: yyyy-MM-dd HH:mm): ");
	    String inicioStr = scanner.nextLine();

	    System.out.println("Ingrese la fecha y hora de fin del turno (formato: yyyy-MM-dd HH:mm): ");
	    String finStr = scanner.nextLine();

	    try {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	        LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
	        LocalDateTime fin = LocalDateTime.parse(finStr, formatter);

	        Turno nuevoTurno = new Turno(tipo, inicio, fin);
	        turnosDisponibles.add(nuevoTurno);
	        
	        System.out.println("Turno creado exitosamente:");
	        System.out.println(nuevoTurno);
	        System.out.println("Desea añadir un turno a un empleado? (s/n)");
	        String op=scanner.nextLine();
	        if (op.equalsIgnoreCase("s")) {
	            AsignarNuevoTurno();
	        }

	    // Usamos una excepcion por si se ingresa incorrectamente la fecha, si no se hace se produciria error
	    } catch (Exception e) {
	        System.out.println("Error al crear el turno. Asegurese de ingresar los datos en el formato correcto.");
	    }
	}
	/**
     * FUNCION QUE PERMITE ASIGNAR UN TURNO A UN EMPLEADO DADO
     */
	public void AsignarNuevoTurno() {
	    System.out.println("\n== Asignar nuevo turno a un empleado ==");
	    
	    // Leemos y mostramos los empleados actuales
	    ArchivoPlano archivoPlano = new ArchivoPlano();
	    ArrayList<String> empleadosActuales = archivoPlano.leer("datos/empleados.csv");

	    System.out.println("Lista de empleados:");
	    for (int i = 0; i < empleadosActuales.size(); i++) {
	        String[] partes = empleadosActuales.get(i).split(",");
	        if (partes.length >= 4) {
	            System.out.println((i + 1) + ". " + partes[3] + " (ID: " + partes[4] + ")");
	        }
	    }

	    System.out.print("Seleccione el numero del empleado al que desea asignar un nuevo turno: ");
	    int opcionEmpleado = Integer.parseInt(scanner.nextLine()) - 1;

	    if (opcionEmpleado < 0 || opcionEmpleado >= empleadosActuales.size()) {
	        System.out.println("Empleado no válido.");
	        return;
	    }

	    // Mostramos los turnos previamente creados en el metodo crearTurno
	    if (turnosDisponibles.isEmpty()) {
	        System.out.println("No hay turnos disponibles. Por favor cree uno primero.");
	        return;
	    }

	    System.out.println("\nTurnos disponibles:");
	    for (int i = 0; i < turnosDisponibles.size(); i++) {
	        System.out.println((i + 1) + ". " + turnosDisponibles.get(i));
	    }

	    System.out.print("Seleccione el numero del turno que desea asignar: ");
	    int opcionTurno = Integer.parseInt(scanner.nextLine()) - 1;

	    if (opcionTurno < 0 || opcionTurno >= turnosDisponibles.size()) {
	        System.out.println("Turno no valido.");
	        return;
	    }

	    Turno turnoSeleccionado = turnosDisponibles.get(opcionTurno);

	    // Actualizamos la informacion del csv con el nuevo turno y guardamos el CSV
	    String lineaOriginal = empleadosActuales.get(opcionEmpleado);
	    String[] partes = lineaOriginal.split(",");
	    if (partes.length < 7) {
	        System.out.println("Error al procesar el empleado.");
	        return;
	    }

	    partes[6] = turnoSeleccionado.toString();
	    String nuevaLinea = String.join(",", partes);
	    empleadosActuales.set(opcionEmpleado, nuevaLinea);

	    archivoPlano.escribir("datos/empleados.csv", empleadosActuales);
	    System.out.println("Turno asignado exitosamente a " + partes[3]);
	}
	
	/**
     * FUNCION QUE PERMITE CREAR UN NUEVO EMPLEADO
     */
	public void CrearEmpleado() {
	    // Registro manual de empleado
	    System.out.println("\n== Registro de nuevo empleado ==");

	    System.out.print("Ingrese el nombre: ");
	    String nombreEmpleado = scanner.nextLine();

	    System.out.print("Ingrese login: ");
	    String login = scanner.nextLine();

	    System.out.print("Ingrese contraseña: ");
	    String password = scanner.nextLine();

	    System.out.println("Seleccione el tipo de empleado a registrar:");
	    System.out.println("1. Cajero\n2. Cocinero\n3. Operador Mecanico\n4. Servicio General");
	    int tipo = scanner.nextInt();
	    scanner.nextLine();

	    Empleado nuevoEmpleado = null;

	    if (tipo == 1) {
	        nuevoEmpleado = new Cajero(login, password, nombreEmpleado, 1, "Taquilla", null, true);
	    } else if (tipo == 2) {
	        nuevoEmpleado = new Cocinero(login, password, nombreEmpleado, 1, "Cocina", null, true);
	    } else if (tipo == 3) {
	        nuevoEmpleado = new OperadorMecanico(login, password, nombreEmpleado, 1, "MontanaRusa", true, atraccionMecanica, null);
	    } else if (tipo == 4) {
	        nuevoEmpleado = new ServicioGeneral(login, password, nombreEmpleado, 1, "Baños", null);
	    } else {
	        System.out.println("Tipo de empleado invalido.");
	        return;
	    }

	    // Mostrar turnos disponibles
	    if (turnosDisponibles.isEmpty()) {
	        System.out.println("No hay turnos disponibles actualmente.");
	        return;
	    }

	    System.out.println("Seleccione el numero del turno que desea asignar al empleado:");
	    for (int i = 0; i < turnosDisponibles.size(); i++) {
	        System.out.println((i + 1) + ". " + turnosDisponibles.get(i));
	    }

	    int seleccionTurno = scanner.nextInt();
	    scanner.nextLine();

	    if (seleccionTurno < 1 || seleccionTurno > turnosDisponibles.size()) {
	        System.out.println("Selección de turno invalida.");
	        return;
	    }

	    Turno turnoSeleccionado = turnosDisponibles.get(seleccionTurno - 1);
	    nuevoEmpleado.asignarTurno(turnoSeleccionado.getHoraInicio().toLocalDate(), turnoSeleccionado);
	    nuevoEmpleado.setTurno(turnoSeleccionado); 

	    // Agregar a la lista
	    empleados.add(nuevoEmpleado);

	    // Guardar solo este nuevo empleado en el archivo
	    ArchivoPlano archivoPlano = new ArchivoPlano();
	    archivoPlano.escribirEmpleadoAppend("datos/empleados.csv", nuevoEmpleado);

	    System.out.println("Empleado registrado exitosamente.");
	}
	
}