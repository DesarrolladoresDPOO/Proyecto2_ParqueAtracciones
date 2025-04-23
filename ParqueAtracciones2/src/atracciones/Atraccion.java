package atracciones;

public abstract class Atraccion {

	private String nombre;
	private int cupoMaximo;
	private int empleadosEncargados;
	private boolean disponibleClima;
	private String nivelExclusividad;
	private Ubicacion ubicacion;
	
	// Getters y Setters
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCupoMaximo() {
		return cupoMaximo;
	}
	public void setCupoMaximo(int cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}
	public int getEmpleadosEncargados() {
		return empleadosEncargados;
	}
	public void setEmpleadosEncargados(int empleadosEncargados) {
		this.empleadosEncargados = empleadosEncargados;
	}
	public boolean isDisponibleClima() {
		return disponibleClima;
	}
	public void setDisponibleClima(boolean disponibleClima) {
		this.disponibleClima = disponibleClima;
	}
	public String getNivelExclusividad() {
		return nivelExclusividad;
	}
	public void setNivelExclusividad(String nivelExclusividad) {
		this.nivelExclusividad = nivelExclusividad;
	}
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	// Constructor clase abstracta
	public Atraccion(String nombre, int cupoMaximo, int empleadosEncargados, boolean disponibleClima, String nivelExclusividad) {
		super();
		this.nombre = nombre;
		this.cupoMaximo = cupoMaximo;
		this.empleadosEncargados = empleadosEncargados;
		this.disponibleClima = disponibleClima;
		this.nivelExclusividad = nivelExclusividad;
	}
	
}
