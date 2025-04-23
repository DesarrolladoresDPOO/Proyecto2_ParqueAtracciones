package persona;

import java.util.ArrayList;
import java.util.List;

import atracciones.AtraccionMecanica;

public class OperadorMecanico extends Empleado {

    private List<AtraccionMecanica> atraccionesCapacitadas;

    public OperadorMecanico(String login, String password, String nombre, int Id, String lugarTrabajo, boolean capacitacionInicial, AtraccionMecanica atraccionInicial, Turno turno) {
    	super(login, password, nombre, Id, lugarTrabajo, turno);
        this.atraccionesCapacitadas = new ArrayList<>();
        
        // Si se indica que tiene una capacitación inicial, la agregamos
        if (capacitacionInicial && atraccionInicial != null) {
            atraccionesCapacitadas.add(atraccionInicial);
        }
    }

    public void agregarCapacitacion(AtraccionMecanica atraccion) {
        if (atraccion != null && !atraccionesCapacitadas.contains(atraccion)) {
            atraccionesCapacitadas.add(atraccion);
        }
    }

    public boolean puedeOperar(AtraccionMecanica atraccion) {
        return atraccionesCapacitadas.contains(atraccion);
    }
    
}
