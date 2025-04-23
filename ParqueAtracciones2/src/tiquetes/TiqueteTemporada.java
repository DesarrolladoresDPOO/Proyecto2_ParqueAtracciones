package tiquetes;

import java.util.Date;

public class TiqueteTemporada extends Tiquete {

    // Atributos
    private Date fechaInicio;
    private Date fechaFin;
    private String categoria;
    private double descuento;

    // Constructor
    public TiqueteTemporada(Date fechaInicio, Date fechaFin, String categoria, double descuento, boolean fastPass) {
        super("Temporada", fastPass);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.categoria = categoria;
        this.descuento = descuento;
    }

    // Getters
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getCategoria() { 
        return categoria;
    }

    public double getDescuento() {
        return descuento;
    }
}