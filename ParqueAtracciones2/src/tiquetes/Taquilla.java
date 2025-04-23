package tiquetes;

import java.util.ArrayList;
import java.util.List;
import persona.Cajero;

public class Taquilla {

    // Atributo
    private List<Cajero> cajeros;
    private List<String> ventas = new ArrayList<>();

    // Constructor
    public Taquilla(List<Cajero> cajerosIniciales) {
        this.cajeros = new ArrayList<Cajero>(cajerosIniciales);
    }

    // Getter
    public List<Cajero> getCajeros() {
        return cajeros;
    }
    
    public List<String> getVentas() {
		return ventas;
	}

	public void setVentas(List<String> ventas) {
		this.ventas = ventas;
	}

	public void setCajeros(List<Cajero> cajeros) {
		this.cajeros = cajeros;
	}

	// Métodos
    public void asignarCajero(Cajero cajero) {
        cajeros.add(cajero);
    }

	public void registrarVenta(String producto, double valor) {
	    ventas.add("Producto: " + producto + ", Valor: " + valor);
	}
}