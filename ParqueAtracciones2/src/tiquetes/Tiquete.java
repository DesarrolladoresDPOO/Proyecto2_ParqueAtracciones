package tiquetes;

public abstract class Tiquete {
    
    // Atributos
    protected boolean fastPass;
    protected boolean usado;
    protected String tipo;

    // Constructor
    public Tiquete(String tipo, boolean fastPass) {
        this.tipo = tipo;
        this.fastPass = fastPass;
        this.usado = false; // Por defecto, un tiquete nuevo no ha sido usado
    }

    // Método para usar el tiquete
    public void usarTiquete() {
        this.usado = true;
    }

    // Verifica si el tiquete ya fue usado
    public boolean isUsado() {
        return usado;
    }

    // Getter del tipo de tiquete
    public String getTipo() {
        return tipo;
    }

    // Getter del fastPass
    public boolean hasFastPass() {
        return fastPass;
    }
}