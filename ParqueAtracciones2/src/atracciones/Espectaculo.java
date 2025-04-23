package atracciones;

import java.time.LocalDateTime;

public class Espectaculo {
	
	public String nombre;
	public Temporada temporada;
	public String horarios;
	public boolean fijaTemporada;
	
	// Getters y Setters
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getHorarios() {
		return horarios;
	}
	public void setHorarios(String horarios) {
		this.horarios = horarios;
	}
	public boolean isFijaTemporada() {
		return fijaTemporada;
	}
	public void setFijaTemporada(boolean fijaTemporada) {
		this.fijaTemporada = fijaTemporada;
	}
	public Temporada getTemporada() {
		return temporada;
	}
	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}
	
	// Constructor

	public Espectaculo(String nombre, Temporada temporada, String horarios, boolean fijaTemporada) {
		this.nombre = nombre;
		this.temporada = temporada;
		this.horarios = horarios;
		this.fijaTemporada = fijaTemporada;
	}
	
	// Comparamos la fecha de hoy para saber si el espectaculo se encuentra disponible
	
	public boolean estaDisponible() {
	    LocalDateTime fechaActual = LocalDateTime.now();
	    return fechaActual.isAfter(getTemporada().getFechaInicio()) && fechaActual.isBefore(getTemporada().getFechaFin());
	}
	
}
