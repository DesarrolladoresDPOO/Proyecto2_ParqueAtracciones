package pruebas;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.*;

import Interfaz.InterfazAdmin;

public class TestIntegracionAdministrador {

    private final String empleadosPath = "datos/empleados.csv";
    private final String atraccionesPath = "datos/atracciones.csv";

    @BeforeEach
    void setUp() throws IOException {
        // Creamos carpeta y archivos mock si no existen
        Files.createDirectories(Paths.get("datos"));

        // Creamos un administrador de prueba
        List<String> empleadosData = List.of("Administrador,admin1,adminpass,Juan,1001,Oficina,NULL");
        Files.write(Paths.get(empleadosPath), empleadosData, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // Archivo vacío de atracciones
        Files.write(Paths.get(atraccionesPath), new ArrayList<>(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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
        );

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

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(empleadosPath));
        Files.deleteIfExists(Paths.get(atraccionesPath));
    }
}