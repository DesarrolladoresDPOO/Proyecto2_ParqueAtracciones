package persona;

public class Cocinero extends Empleado {
	private boolean Capacitacion;

	public Cocinero(String login, String password, String nombre, int Id, String lugarTrabajo, Turno turno, boolean capacitacion) {
		super(login, password, nombre, Id, lugarTrabajo, turno);
		Capacitacion = capacitacion;
	}
	
	public Boolean getCapacitacion() {
		return Capacitacion;
	}
	
	public void setCapacitacion(Boolean capacitado) {
		this.Capacitacion = capacitado;
	}
	
	public void PrepararComida() {
		System.out.println("Preparando comida");
		System.out.println("Comida preparada");
		
	}
}
