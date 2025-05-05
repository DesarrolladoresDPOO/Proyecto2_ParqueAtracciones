package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import persona.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestIntegracionEmpleado {

    @Test
    public void flujoEmpleado() throws Exception {
        // Crear un cajero con datos mínimos
        Cajero cajero = new Cajero("Juan1", "123", "Juan Quijano", 101, "CajaEntrada", null, true);

        // Crear turno
        LocalDateTime inicioDiurno = LocalDateTime.of(2025, 4, 2, 8, 0);
		LocalDateTime finDiurno = LocalDateTime.of(2025, 4, 2, 16, 0);
        Turno turnoDiurno = new Turno("Diurno", inicioDiurno, finDiurno);
        
        LocalDate fechaTurno = LocalDate.of(2025, 4, 2);

        // Asignar turno
        cajero.asignarTurno(fechaTurno, turnoDiurno);

        // Verificar turno
        Turno turnoObtenido = cajero.consultarTurno(fechaTurno);
        assertNotNull(turnoObtenido);
        assertEquals("Diurno", turnoObtenido.getTipo());
    }
}
