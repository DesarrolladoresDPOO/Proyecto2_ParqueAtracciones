package tiquetes;

public class EntradaIndividual extends Tiquete {

    // Atributo
    private String nombreAtraccion;

    // Constructor
    public EntradaIndividual(String nombreAtraccion, boolean fastPass) {
        super("EntradaIndividual", fastPass);
        this.nombreAtraccion = nombreAtraccion;
    }

    // Getter
    public String getNombreAtraccion() {
        return nombreAtraccion;
    }

    // Método para usar la entrada
    public void usarEntrada() {
        usarTiquete(); // Marca el tiquete como usado
    }
}