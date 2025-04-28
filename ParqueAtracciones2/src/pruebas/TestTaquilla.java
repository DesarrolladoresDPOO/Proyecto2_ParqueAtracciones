package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tiquetes.Taquilla;
import persona.Cajero;
import persona.Turno;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestTaquilla {

    @Test
    public void testConstructorTaquilla() {
        List<Cajero> cajerosIniciales = new ArrayList<>();

        Turno turno = new Turno("Diurno", LocalDateTime.of(2025, 4, 28, 8, 0), LocalDateTime.of(2025, 4, 28, 16, 0));
        Cajero cajero1 = new Cajero("cajero1", "pass1", "Pedro", 101, "Taquilla Norte", turno, true);
        cajerosIniciales.add(cajero1);

        Taquilla taquilla = new Taquilla(cajerosIniciales);

        assertEquals(1, taquilla.getCajeros().size());
        assertTrue(taquilla.getCajeros().contains(cajero1));
    }

    @Test
    public void testAsignarCajero() {
        List<Cajero> cajerosIniciales = new ArrayList<>();
        Taquilla taquilla = new Taquilla(cajerosIniciales);

        Turno turno = new Turno("Nocturno", LocalDateTime.of(2025, 4, 28, 18, 0), LocalDateTime.of(2025, 4, 29, 2, 0));
        Cajero nuevoCajero = new Cajero("cajero2", "pass2", "Ana", 102, "Taquilla Sur", turno, false);
        taquilla.asignarCajero(nuevoCajero);

        assertEquals(1, taquilla.getCajeros().size());
        assertTrue(taquilla.getCajeros().contains(nuevoCajero));
    }

    @Test
    public void testRegistrarVenta() {
        List<Cajero> cajerosIniciales = new ArrayList<>();
        Taquilla taquilla = new Taquilla(cajerosIniciales);

        taquilla.registrarVenta("Entrada Parque", 50000.0);

        assertEquals(1, taquilla.getVentas().size());
        assertTrue(taquilla.getVentas().get(0).contains("Producto: Entrada Parque"));
        assertTrue(taquilla.getVentas().get(0).contains("Valor: 50000.0"));
    }
}