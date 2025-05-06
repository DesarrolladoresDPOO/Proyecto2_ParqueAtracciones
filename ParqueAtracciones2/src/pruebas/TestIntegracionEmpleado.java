package pruebas;

import Interfaz.InterfazEmpleado;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntegracionEmpleado {

    private final String empleadosPath = "datos/empleados.csv";
    private final String clientesPath = "datos/clientes.csv";
    private final String asistenciasPath = "datos/asistencias_clientes.csv";
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() throws IOException {
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(output));

        Files.createDirectories(Paths.get("datos"));

        // Asegurar empleado valido
        List<String> empleados = Files.exists(Paths.get(empleadosPath)) ?
            Files.readAllLines(Paths.get(empleadosPath)) : new ArrayList<>();
        empleados.removeIf(line -> line.contains("Jero123"));
        empleados.add("Cajero,Jero123,pass8,Jerónimo,2001,Taquilla,Diurno (" +
            LocalDateTime.now().withHour(8).withMinute(0).toString() +
            " - " + LocalDateTime.now().withHour(16).withMinute(0).toString() + ")");
        Files.write(Paths.get(empleadosPath), empleados, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // Asegurar cliente valido
        List<String> clientes = Files.exists(Paths.get(clientesPath)) ?
            Files.readAllLines(Paths.get(clientesPath)) : new ArrayList<>();
        clientes.removeIf(line -> line.contains("ClienteTest"));
        clientes.add("clienteuser,cliente123,ClienteTest,Oro");
        Files.write(Paths.get(clientesPath), clientes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // Limpiar asistencias
        Files.write(Paths.get(asistenciasPath), new ArrayList<>(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testFlujoEmpleadoCompleto() {
        String fechaActual = java.time.LocalDate.now().toString();
        String entradas = String.join("\n",
            "Jero123", "pass8",         // autenticacion
            "2",                        // marcar asistencia
            "clienteuser", "cliente123",
            "3",                        // ver historial
            "clienteuser", "cliente123",
            "4",                        // ver turno
            "Jerónimo", fechaActual,
            "0", "0"                    // salida segura
        ) + "\n";

        System.setIn(new ByteArrayInputStream(entradas.getBytes()));

        InterfazEmpleado interfaz = new InterfazEmpleado();
        interfaz.autenticarEmpleado();

        String salida = output.toString();

        assertTrue(salida.contains("Autenticación exitosa"), "Fallo en autenticación.");
        assertTrue(salida.contains("Asistencia del cliente registrada") || salida.contains("ya ha sido registrado"), "Fallo en asistencia.");
        assertTrue(salida.contains("Historial de asistencias de ClienteTest"), "Fallo en historial.");
        assertTrue(salida.contains("tiene un turno asignado"), "Fallo en ver turno.");
    }
}

