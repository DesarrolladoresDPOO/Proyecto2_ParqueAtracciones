package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import persona.*;
import atracciones.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestIntegracionAdministrador {

    @Test
    public void flujoAdministrador() throws Exception {
    	
    	// Crear turno
        LocalDateTime inicioDiurno = LocalDateTime.of(2025, 4, 2, 8, 0);
		LocalDateTime finDiurno = LocalDateTime.of(2025, 4, 2, 16, 0);
        Turno turnoDiurno = new Turno("Diurno", inicioDiurno, finDiurno);
        
        // Crear administrador
        Administrador admin = new Administrador("CamilaS1", "123","Camila Serrano", 1001, "Gerencia", turnoDiurno);

        // Crear atracción mecánica
        AtraccionMecanica atraccion = new AtraccionMecanica("Montaña Rusa", 30, 5, true, "Alta",120, 200, 30, 100, "A,B,C", "Alta");

        // Crear empleado y lugar
        OperadorMecanico operador = new OperadorMecanico("Juan123", "123", "Juan Carlos Restrepo", 1001, "Mantenimiento", true, atraccion, turnoDiurno);
        LocalDate fechaTurno = LocalDate.of(2025, 4, 2);
        // Asignar turno
        admin.asignarTurno(operador, fechaTurno, turnoDiurno);

        // Verificar turno asignado
        Turno turno = operador.consultarTurno(fechaTurno);
        assertEquals("Diurno", turno.getTipo());
    }
}
