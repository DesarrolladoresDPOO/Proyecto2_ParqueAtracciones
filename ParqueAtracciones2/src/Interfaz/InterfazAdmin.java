package Interfaz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import atracciones.AtraccionMecanica;
import atracciones.Atraccion;
import atracciones.AtraccionCultural;

import persistencia.ArchivoPlano;

public class InterfazAdmin {
	private Scanner scanner = new Scanner(System.in);
	private AtraccionMecanica atraccionMecanica;
	private AtraccionCultural atraccionCultural;
	private List<Atraccion> atracciones;
	
	// Objeto para lectura de archivos
    private ArchivoPlano archivoPlano;
    
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
            iniciar(); // Aquí llamas a tu método iniciar()
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
		        System.out.println("Línea inválida (campos insuficientes): " + linea);
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
		                System.out.println("Línea inválida para atracción mecánica: " + linea);
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
		                System.out.println("Línea inválida para atracción cultural: " + linea);
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
		            System.out.println("Tipo de atracción desconocido: " + tipoExistente);
		    }
		}
		
		System.out.println("Las atracciones se han cargado correctamente");
		
		while (true) {
			
			System.out.println("========================================");
			System.out.println("|        MENÚ DE ADMINISTRADOR         |");
			System.out.println("========================================");
			System.out.println("| 1. Crear atracción                   |");
			System.out.println("| 2. Modificar atracción               |");
			System.out.println("| 3. Eliminar atracción                |");
			System.out.println("| 4. Crear nuevo turno                 |");
			System.out.println("| 5. Asignar turno a empleado          |");
			System.out.println("| 0. Salir                             |");
			System.out.println("========================================");
			System.out.print ("Seleccione una opción: ");
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
		/**
         * MENU QUE PERMITE CREAR UNA NUEVA ATRACCION
         */
	    System.out.println("Seleccione el tipo de atraccion que desea registrar:");
	    System.out.println("1. Atracción Mecánica");
	    System.out.println("2. Atracción Cultural");
	    int opcion = scanner.nextInt();
	    scanner.nextLine();

	    Atraccion nuevaAtraccion = null;

	    System.out.print("Ingrese el nombre de la atraccion: ");
	    String nombre = scanner.nextLine();

	    // Verificamos que el nombre de la nueva atraccion sea nuevo
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

	    System.out.print("¿La atraccion está disponible en condiciones climaticas adversas? (true/false): ");
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
	private void modificarAtraccion() {
	    System.out.println("== Modificar atracción ==");

	    System.out.print("Ingrese el nombre de la atracción a modificar: ");
	    String nombreModificar = scanner.nextLine();

	    archivoPlano = new ArchivoPlano();
	    ArrayList<String> atraccionesActuales = archivoPlano.leer("datos/atracciones.csv");

	    boolean encontrada = false;
	    ArrayList<String> atraccionesModificadas = new ArrayList<>();

	    for (String linea : atraccionesActuales) {
	        String[] partes = linea.split(",");

	        if (partes.length >= 12 && partes[1].equalsIgnoreCase(nombreModificar)) {
	            encontrada = true;

	            // Mostramos los datos actuales
	            System.out.println("\nDatos actuales de la atracción:");
	            System.out.println("Atraccion de tipo: " + partes[0]);
	            System.out.println("Nombre: " + partes[1]);
	            System.out.println("Cupo máximo: " + partes[2]);
	            System.out.println("Empleados encargados: " + partes[3]);
	            System.out.println("Disponible con clima adverso: " + partes[4]);
	            System.out.println("Nivel de exclusividad: " + partes[5]);
	            System.out.println("Altura mínima (cm): " + partes[6]);
	            System.out.println("Altura máxima (cm): " + partes[7]);
	            System.out.println("Peso mínimo (kg): " + partes[8]);
	            System.out.println("Peso máximo (kg): " + partes[9]);
	            System.out.println("Restricciones de salud: " + partes[10]);
	            System.out.println("Nivel de riesgo: " + partes[11]);

	            // Solicitamos los nuevos datos
	            System.out.println("\n== Ingrese los nuevos datos de la atraccion ==");

	            System.out.print("Ingrese el tipo de atracción (Cultural/Mecánica): ");
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

	            System.out.print("Ingrese la altura mínima (cm): ");
	            int minAltura = scanner.nextInt();

	            System.out.print("Ingrese la altura máxima (cm): ");
	            int maxAltura = scanner.nextInt();

	            System.out.print("Ingrese el peso mínimo (kg): ");
	            int minPeso = scanner.nextInt();

	            System.out.print("Ingrese el peso máximo (kg): ");
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
	        System.out.println("\nLa atracción ha sido modificada exitosamente.");
	    } else {
	        System.out.println("\nNo se encontró ninguna atracción registrada con ese nombre.");
	    }
	}

	/**
     * FUNCION PARA ELIMINAR UNA ATRACCION EN ESPECIFICO
     */
	private void eliminarAtraccion() {
	    System.out.println("== Eliminar atracción ==");

	    System.out.print("Ingrese el nombre de la atracción que desea eliminar: ");
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
	        System.out.println("Atracción eliminada exitosamente: " + nombreAEliminar);
	    } else {
	        System.out.println("No se encontró una atracción con ese nombre.");
	    }
	}
}