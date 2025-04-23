package persona;

import java.time.LocalDateTime;

public class Turno {
    private String tipo; // "Diurno" o "Nocturno"
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;

    // Constructor
    public Turno(String tipo, LocalDateTime horaInicio, LocalDateTime horaFin) {
        this.tipo = tipo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin; 
    }
    
    // Getters y setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return tipo + " (" + horaInicio + " - " + horaFin + ")";
    }
}
