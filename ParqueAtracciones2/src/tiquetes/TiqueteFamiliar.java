package tiquetes;

import java.util.List;
import atracciones.Atraccion;

public class TiqueteFamiliar extends Tiquete {

    // Atributo
    private List<Atraccion> atraccionesFamiliares;

    // Constructor
    public TiqueteFamiliar(List<Atraccion> atraccionesFamiliares, boolean fastPass) {
        super("Familiar", fastPass);
        this.atraccionesFamiliares = atraccionesFamiliares;
    }

    // Getter
    public List<Atraccion> getAtraccionesFamiliares() {
        return atraccionesFamiliares;
    }
}