package persona;

public abstract class LugarTrabajo {
    
    private String nombre;

    public LugarTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
