package tiquetes;

import java.util.List;
import atracciones.Atraccion;

public class TiqueteDiamante extends Tiquete {

    // Atributo
    private List<Atraccion> atraccionesDiamante;

    // Constructor
    public TiqueteDiamante(List<Atraccion> atraccionesDiamante, boolean fastPass) {
        super("Diamante", fastPass);
        this.atraccionesDiamante = atraccionesDiamante;
    }

    // Getters y setters
    public List<Atraccion> getAtraccionesDiamante() {
        return atraccionesDiamante;
    }

	public void setAtraccionesDiamante(List<Atraccion> atraccionesDiamante) {
		this.atraccionesDiamante = atraccionesDiamante;
	}
    
}