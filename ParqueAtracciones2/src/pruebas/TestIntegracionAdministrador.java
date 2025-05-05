package pruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Interfaz.InterfazAdmin;
import persona.*;
import atracciones.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    
    private final String empleadosPath = "datos/empleados.csv";
    private final String atraccionesPath = "datos/atracciones.csv";

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(Paths.get("datos"));

        // Asegurar archivo empleados.csv
        Path empleadosFile = Paths.get(empleadosPath);
        if (!Files.exists(empleadosFile)) Files.createFile(empleadosFile);

        List<String> empleadosActuales = Files.readAllLines(empleadosFile);
        boolean adminYaExiste = empleadosActuales.stream()
            .anyMatch(line -> line.startsWith("Administrador,admin1,adminpass"));
        if (!adminYaExiste) {
            Files.write(empleadosFile, List.of("Administrador,admin1,adminpass,Juan,1001,Oficina,NULL"), StandardOpenOption.APPEND);
        }

        // Limpiar línea duplicada de atracción
        Path atraccionesFile = Paths.get(atraccionesPath);
        if (!Files.exists(atraccionesFile)) Files.createFile(atraccionesFile);

        List<String> atraccionesActuales = Files.readAllLines(atraccionesFile);
        List<String> filtradas = new ArrayList<>();

        for (String linea : atraccionesActuales) {
            if (!linea.contains("Montaña Loca")) {
                filtradas.add(linea);
            }
        }

        Files.write(atraccionesFile, filtradas, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Test
    void testFlujoCompletoAdministrador() {
        String simulatedInput = String.join(System.lineSeparator(),
            "admin1",                   // Usuario
            "adminpass",                // Contraseña
            "1",                        // Opción: Crear atracción
            "1",                        // Tipo: Mecánica
            "Montaña Loca",            // Nombre
            "20",                      // Cupo máximo
            "3",                       // Empleados encargados
            "true",                    // Clima
            "Oro",                     // Exclusividad
            "130", "200", "40", "100", // Altura/peso
            "ninguna",                 // Restricciones salud
            "alto",                    // Nivel riesgo
            "0"                        // Salir
        ) + System.lineSeparator();

        // Redireccionamos entrada y salida
        InputStream stdinBackup = System.in;
        PrintStream stdoutBackup = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        // Ejecutamos la prueba
        InterfazAdmin interfaz = new InterfazAdmin();
        interfaz.autenticarYIniciar();

        // Restauramos
        System.setIn(stdinBackup);
        System.setOut(stdoutBackup);

        String output = out.toString();

        // Verificamos que la atracción se haya registrado exitosamente
        assertTrue(output.contains("Autenticacion exitosa"));
        assertTrue(output.contains("La atraccion ha sido registrada exitosamente: Montaña Loca"));
    }
}
