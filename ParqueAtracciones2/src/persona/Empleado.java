package persona;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public abstract class Empleado extends Usuario {

	private String nombre;
	private int Id;
	private Map<LocalDate, Turno> turnosAsignados = new HashMap<>();
	private String lugarTrabajo;
	public Turno turno;

	//Constructor
	
	public Empleado(String login, String password, String nombre, int Id, String lugarTrabajo, Turno turno) {
		super(login, password);
		this.nombre = nombre;
		this.Id = Id;
		this.lugarTrabajo = lugarTrabajo;
		this.turno = turno;
	}

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}
	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	// Metodos para turnos
	public Turno consultarTurno(LocalDate fecha) {
		return turnosAsignados.get(fecha);
	}

	public void asignarTurno(LocalDate fecha, Turno turno) {
		turnosAsignados.put(fecha, turno);
	}
	
	public void setLugarTrabajo(String lugarTrabajo) {
		this.lugarTrabajo = lugarTrabajo;
	}
	
	public String getLugarTrabajo() {
		return lugarTrabajo;
	}
}
