package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import atracciones.Espectaculo;
import atracciones.Temporada;
import java.time.LocalDateTime;

public class TestEspectaculo {

    @Test
    public void testConstructorEspectaculo() {
        LocalDateTime inicio = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 7, 31, 23, 59);
        Temporada temporada = new Temporada(inicio, fin);
        Espectaculo espectaculo = new Espectaculo("Show de Magia", temporada, "15:00 - 17:00", true);

        assertEquals("Show de Magia", espectaculo.getNombre());
        assertEquals(temporada, espectaculo.getTemporada());
        assertEquals("15:00 - 17:00", espectaculo.getHorarios());
        assertTrue(espectaculo.isFijaTemporada());
    }

    @Test
    public void testDisponibilidadEspectaculoActivo() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(2);
        LocalDateTime fin = LocalDateTime.now().plusDays(2);
        Temporada temporada = new Temporada(inicio, fin);
        Espectaculo espectaculo = new Espectaculo("Show de Magia", temporada, "15:00 - 17:00", true);

        assertTrue(espectaculo.estaDisponible());
    }

    @Test
    public void testDisponibilidadEspectaculoInactivo() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(10);
        LocalDateTime fin = LocalDateTime.now().minusDays(5);
        Temporada temporada = new Temporada(inicio, fin);
        Espectaculo espectaculo = new Espectaculo("Show de Magia", temporada, "15:00 - 17:00", true);

        assertFalse(espectaculo.estaDisponible());
    }
}