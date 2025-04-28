package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tiquetes.VentaOnline;
import tiquetes.Cliente;
import tiquetes.TiqueteBasico;
import tiquetes.Tiquete;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestVentaOnline {

    @Test
    public void testConstructorVentaOnline() {
        List<Tiquete> tiquetes = new ArrayList<>();
        Cliente cliente = new Cliente("user123", "pass123", "Juan Perez", tiquetes);
        Date fechaCompra = new Date();
        VentaOnline venta = new VentaOnline(cliente, "Tarjeta Crédito", fechaCompra, 100000.0);

        assertEquals(cliente, venta.getCliente());
        assertEquals("Tarjeta Crédito", venta.getMetodoPago());
        assertEquals(fechaCompra, venta.getFechaCompra());
        assertEquals(100000.0, venta.getTotal(), 0.01);
    }

    @Test
    public void testProcesarPago() {
        List<Tiquete> tiquetes = new ArrayList<>();
        Cliente cliente = new Cliente("user123", "pass123", "Juan Perez", tiquetes);
        Date fechaCompra = new Date();
        VentaOnline venta = new VentaOnline(cliente, "Tarjeta Crédito", fechaCompra, 100000.0);

        assertTrue(venta.procesarPago());
    }

    @Test
    public void testGenerarFactura() {
        List<Tiquete> tiquetes = new ArrayList<>();
        Cliente cliente = new Cliente("user123", "pass123", "Juan Perez", tiquetes);
        Date fechaCompra = new Date();
        VentaOnline venta = new VentaOnline(cliente, "Tarjeta Crédito", fechaCompra, 100000.0);

        String factura = venta.generarFactura();

        assertTrue(factura.contains("Cliente: Juan Perez"));
        assertTrue(factura.contains("Método de pago: Tarjeta Crédito"));
        assertTrue(factura.contains("Total: $100000.0"));
    }
}