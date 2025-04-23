package tiquetes;

import java.util.List;

import persona.Usuario;

public class Cliente extends Usuario{

    // Atributos
    private String nombre;
    private List<Tiquete> tiquetes;

    // Constructor
    public Cliente(String login, String password, String nombre, List<Tiquete> tiquetes) {
		super(login, password);
		this.nombre = nombre;
		this.tiquetes = tiquetes;
	}

	// Getter del nombre
    public String getNombre() {
        return nombre;
    }

    // Getter de los tiquetes
    public List<Tiquete> getTiquetes() {
        return tiquetes;
    }

    // Comprar tiquete: lo añade a la lista
    public void comprarTiquete(Tiquete tiquete) {
        tiquetes.add(tiquete);
    }

    // Usar un tiquete (retorna true si se pudo usar, false si ya estaba usado)
    public boolean usarTiquete(Tiquete tiquete) {
        if (tiquetes.contains(tiquete) && !tiquete.isUsado()) {
            tiquete.usarTiquete();
            return true;
        }
        return false;
    }
}