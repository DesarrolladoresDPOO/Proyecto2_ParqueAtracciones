package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import atracciones.AtraccionCultural;
import atracciones.Temporada;
import java.time.LocalDateTime;

public class TestAtraccionCultural {

    @Test
    public void testConstructorAtraccionCultural() {
        AtraccionCultural atraccion = new AtraccionCultural(
            "Museo de Cera", 20, 2, true, "Media", 12
        );

        assertEquals("Museo de Cera", atraccion.getNombre());
        assertEquals(20, atraccion.getCupoMaximo());
        assertEquals(2, atraccion.getEmpleadosEncargados());
        assertTrue(atraccion.isDisponibleClima());
        assertEquals("Media", atraccion.getNivelExclusividad());
        assertEquals(12, atraccion.getEdadMinima());
    }

    @Test
    public void testAptaParaClienteCumpleEdad() {
        AtraccionCultural atraccion = new AtraccionCultural(
            "Museo de Cera", 20, 2, true, "Media", 12
        );

        atraccion.aptaParaCliente(15); // tiene más edad que el mínimo
        assertTrue(atraccion.isRespuesta());
    }

    @Test
    public void testAptaParaClienteNoCumpleEdad() {
        AtraccionCultural atraccion = new AtraccionCultural(
            "Museo de Cera", 20, 2, true, "Media", 12
        );

        atraccion.aptaParaCliente(10); // tiene menos edad que el mínimo
        assertFalse(atraccion.isRespuesta());
    }

    @Test
    public void testDisponibilidadAtraccionCultural() {
        AtraccionCultural atraccion = new AtraccionCultural(
            "Museo de Cera", 20, 2, true, "Media", 12
        );

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fin = LocalDateTime.now().plusDays(1);
        Temporada temporada = new Temporada(inicio, fin);

        assertTrue(atraccion.estaDisponible(temporada));
    }
}