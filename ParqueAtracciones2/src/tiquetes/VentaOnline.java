package tiquetes;

import java.util.Date;

public class VentaOnline {

    // Atributos
    private Cliente cliente;
    private String metodoPago;
    private Date fechaCompra;
    private double total;

    // Constructor
    public VentaOnline(Cliente cliente, String metodoPago, Date fechaCompra, double total) {
        this.cliente = cliente;
        this.metodoPago = metodoPago;
        this.fechaCompra = fechaCompra;
        this.total = total;
    }

    // Getters
    public Cliente getCliente() {
        return cliente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public double getTotal() {
        return total;
    }

    // Procesar pago (simulado, siempre retorna true)
    public boolean procesarPago() {
        // Aquí podrías conectar a un sistema real
        return true;
    }

    // Generar factura (formato simple de texto)
    public String generarFactura() {
        return "Factura\nCliente: " + cliente.getNombre() + 
               "\nFecha: " + fechaCompra +
               "\nMétodo de pago: " + metodoPago +
               "\nTotal: $" + total;
    }

    // Enviar confirmación (solo muestra mensaje por ahora)
    public void enviarConfirmacion() {
        System.out.println("Confirmación enviada a " + cliente.getNombre());
    }
}