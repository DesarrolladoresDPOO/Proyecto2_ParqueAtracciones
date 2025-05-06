package pruebas;

import Interfaz.InterfazCliente;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestIntegracionCliente {

    private final String clientesPath = "datos/clientes.csv";
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    public void setUp() throws IOException {
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(output));

        Files.createDirectories(Paths.get("datos"));

        // Asegurar archivo clientes.csv sin entrada duplicada
        Path path = Paths.get(clientesPath);
        if (!Files.exists(path)) Files.createFile(path);

        List<String> lineas = Files.readAllLines(path);
        List<String> filtradas = new ArrayList<>();
        for (String linea : lineas) {
            if (!linea.contains("CarlosTest")) {
                filtradas.add(linea);
            }
        }
        Files.write(path, filtradas, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testFlujoClienteCompleto() {
        String entradas = String.join(System.lineSeparator(),
            "1",             // opcion: comprar tiquete
            "CarlosTest",    // nombre del cliente
            "s",             // desea registrarlo
            "carloslogin",   // login
            "carlospass",    // contraseña
            "s",             // desea FastPass
            "3",             // tipo de tiquete: Oro
            "2",             // consultar tiquetes
            "CarlosTest",    // nombre cliente
            "0"              // salir
        ) + System.lineSeparator();

        // Redirigir entrada/salida
        InputStream stdinBackup = System.in;
        PrintStream stdoutBackup = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(entradas.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        // Ejecutar prueba
        InterfazCliente interfaz = new InterfazCliente();
        interfaz.iniciar();

        // Restaurar entrada/salida
        System.setIn(stdinBackup);
        System.setOut(stdoutBackup);

        String salida = out.toString();

        assertTrue(salida.contains("Cliente registrado exitosamente"), "Fallo en el registro.");
        assertTrue(salida.contains("Tiquete agregado exitosamente"), "Fallo en la compra.");
        assertTrue(salida.contains("Tiquetes de CarlosTest"), "Fallo en la consulta.");
        assertTrue(salida.toLowerCase().contains("oro"), "Tipo de tiquete no mostrado.");
    }
}



