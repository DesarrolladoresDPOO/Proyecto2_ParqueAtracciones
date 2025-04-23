package atracciones;

import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.Arrays;


public class AtraccionMecanica extends Atraccion{
	
	private int minimoAltura;
	private int maximoAltura;
	private int minimoPeso;
	private int maximoPeso;
	// Cadena de restricciones separadas por coma, A,B,C,D
	private String restriccionesSalud;
	private String nivelRiesgo;
	
	// Getters y setters

	public int getMinimoAltura() {
		return minimoAltura;
	}

	public void setMinimoAltura(int minimoAltura) {
		this.minimoAltura = minimoAltura;
	}

	public int getMaximoAltura() {
		return maximoAltura;
	}

	public void setMaximoAltura(int maximoAltura) {
		this.maximoAltura = maximoAltura;
	}

	public int getMinimoPeso() {
		return minimoPeso;
	}

	public void setMinimoPeso(int minimoPeso) {
		this.minimoPeso = minimoPeso;
	}

	public int getMaximoPeso() {
		return maximoPeso;
	}

	public void setMaximoPeso(int maximoPeso) {
		this.maximoPeso = maximoPeso;
	}

	public String getRestriccionesSalud() {
		return restriccionesSalud;
	}

	public void setRestriccionesSalud(String restriccionesSalud) {
		this.restriccionesSalud = restriccionesSalud;
	}

	public String getNivelRiesgo() {
		return nivelRiesgo;
	}

	public void setNivelRiesgo(String nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}

	// Constructor utilizando la superclase
	public AtraccionMecanica(String nombre, int cupoMaximo, int empleadosEncargados, boolean disponibleClima,
			String nivelExclusividad, int minimoAltura, int maximoAltura, int minimoPeso, int maximoPeso,
			String restriccionesSalud, String nivelRiesgo) {
		super(nombre, cupoMaximo, empleadosEncargados, disponibleClima, nivelExclusividad);
		this.minimoAltura = minimoAltura;
		this.maximoAltura = maximoAltura;
		this.minimoPeso = minimoPeso;
		this.maximoPeso = maximoPeso;
		this.restriccionesSalud = restriccionesSalud;
		this.nivelRiesgo = nivelRiesgo;
	}

	private boolean respuesta;

    public void aptaParaCliente(int alturaUsuario, int pesoUsuario, String restriccionesUsuario) {
        int minPeso = getMinimoPeso();
        int maxPeso = getMaximoPeso();
        int minAltura = getMinimoAltura();
        int maxAltura = getMaximoAltura();

        boolean cumplePesoAltura = minPeso < pesoUsuario && pesoUsuario < maxPeso &&
                                   minAltura < alturaUsuario && alturaUsuario < maxAltura;

        boolean tieneRestriccion = tieneRestriccion(restriccionesUsuario);

        if (cumplePesoAltura && !tieneRestriccion) {
            respuesta = true;
        } else {
            respuesta = false;
        }
    }

    private boolean tieneRestriccion(String restriccionesUsuario) {
        // Convierte las restricciones de la atracción en un Set para una busqueda rapida
        Set<String> restriccionesAtraccion = new HashSet<>(Arrays.asList(getRestriccionesSalud().split(",")));

        // Divide las restricciones del usuario y verifica si alguna está en la lista de la atraccion
        for (String restriccion : restriccionesUsuario.split(",")) {
            if (restriccionesAtraccion.contains(restriccion.trim())) {
                return true; // Se encontró una restricción en comun
            }
        }
        return false;
    }

    public boolean isRespuesta() {
        return respuesta;
    }
    
    // Comparamos la fecha de hoy para saber si el espectaculo se encuentra disponible
	
 	public boolean estaDisponible(Temporada temporada) {
 	    LocalDateTime fechaActual = LocalDateTime.now();
 	    return fechaActual.isAfter(temporada.getFechaInicio()) && fechaActual.isBefore(temporada.getFechaFin());
 	}
	
}
