package persona;

import java.util.ArrayList;
import java.util.List;

public class LugarServicio extends LugarTrabajo {

    private String tipo;
    private List<Empleado> empleadosAsignados;

    public LugarServicio(String tipo) {
        super(tipo); // Se usa como nombre también
        this.tipo = tipo;
        this.empleadosAsignados = new ArrayList<>();
    }

    public void asignarEmpleado(Empleado empleado) {
        empleadosAsignados.add(empleado);
    }

    public String getTipo() {
        return tipo;
    }

    public List<Empleado> getEmpleadosAsignados() {
        return empleadosAsignados;
    }
}
