package tiquetes;

import java.util.List;
import atracciones.Atraccion;

public class TiqueteOro extends Tiquete {

    // Atributo
    private List<Atraccion> atraccionesOro;

    // Constructor
    public TiqueteOro(List<Atraccion> atraccionesOro, boolean fastPass) {
        super("Oro", fastPass);
        this.atraccionesOro = atraccionesOro;
    }

    // Getter
    public List<Atraccion> getAtraccionesOro() {
        return atraccionesOro;
    }
}