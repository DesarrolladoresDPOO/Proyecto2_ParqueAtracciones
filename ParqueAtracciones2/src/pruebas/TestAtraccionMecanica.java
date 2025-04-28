package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import atracciones.AtraccionMecanica;
import atracciones.Temporada;
import java.time.LocalDateTime;

public class TestAtraccionMecanica {

    @Test
    public void testConstructorAtraccionMecanica() {
        AtraccionMecanica atraccion = new AtraccionMecanica(
            "Montaña Rusa", 30, 5, true, "Alta",
            120, 200, 30, 100, "A,B,C", "Alta"
        );

        assertEquals("Montaña Rusa", atraccion.getNombre());
        assertEquals(30, atraccion.getCupoMaximo());
        assertEquals(5, atraccion.getEmpleadosEncargados());
        assertTrue(atraccion.isDisponibleClima());
        assertEquals("Alta", atraccion.getNivelExclusividad());
        assertEquals(120, atraccion.getMinimoAltura());
        assertEquals(200, atraccion.getMaximoAltura());
        assertEquals(30, atraccion.getMinimoPeso());
        assertEquals(100, atraccion.getMaximoPeso());
        assertEquals("A,B,C", atraccion.getRestriccionesSalud());
        assertEquals("Alta", atraccion.getNivelRiesgo());
    }

    @Test
    public void testAptaParaClienteCumpleTodo() {
        AtraccionMecanica atraccion = new AtraccionMecanica(
            "Montaña Rusa", 30, 5, true, "Alta",
            120, 200, 30, 100, "A,B,C", "Alta"
        );

        atraccion.aptaParaCliente(150, 60, ""); // altura y peso OK, sin restricciones
        assertTrue(atraccion.isRespuesta());
    }

    @Test
    public void testAptaParaClienteConRestriccion() {
        AtraccionMecanica atraccion = new AtraccionMecanica(
            "Montaña Rusa", 30, 5, true, "Alta",
            120, 200, 30, 100, "A,B,C", "Alta"
        );

        atraccion.aptaParaCliente(150, 60, "B"); // tiene restricción de salud "B"
        assertFalse(atraccion.isRespuesta());
    }

    @Test
    public void testAptaParaClienteFallaPesoAltura() {
        AtraccionMecanica atraccion = new AtraccionMecanica(
            "Montaña Rusa", 30, 5, true, "Alta",
            120, 200, 30, 100, "A,B,C", "Alta"
        );

        atraccion.aptaParaCliente(100, 20, ""); // no cumple peso ni altura
        assertFalse(atraccion.isRespuesta());
    }

    @Test
    public void testDisponibilidadAtraccionMecanica() {
        AtraccionMecanica atraccion = new AtraccionMecanica(
            "Montaña Rusa", 30, 5, true, "Alta",
            120, 200, 30, 100, "A,B,C", "Alta"
        );

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fin = LocalDateTime.now().plusDays(1);
        Temporada temporada = new Temporada(inicio, fin);

        assertTrue(atraccion.estaDisponible(temporada));
    }
}