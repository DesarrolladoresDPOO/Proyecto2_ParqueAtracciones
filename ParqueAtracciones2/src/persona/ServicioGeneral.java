package persona;

public class ServicioGeneral extends Empleado {
	
	public ServicioGeneral(String login, String password, String nombre, int Id, String lugarTrabajo, Turno turno) {
		super(login, password, nombre, Id, lugarTrabajo, turno);
	}

	public void RealizarMantenimiento() {
		System.out.println("Realizando mantenimiento");
		
	}
	
	public void RealizarAseo() {
		System.out.println("Realizando aseo");
		
	}
}
